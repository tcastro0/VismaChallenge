package com.visma.photocapture.presentation.viewmodel

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visma.domain.core.VismaResult
import com.visma.domain.features.photocapture.usecases.SavePhotoCaptureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject


@HiltViewModel
class PhotoCaptureViewModel @Inject constructor(
    private val savePhotoUseCase: SavePhotoCaptureUseCase
) : ViewModel() {

    var photoInfo = mutableStateOf<Pair<Uri, String>?>(null)
        private set

    private var _isCameraInitialized = false
    private var _imageCapture: ImageCapture? = null


    fun setupCameraIfNeeded(
        lifecycleOwner: LifecycleOwner,
        context: Context,
        previewView: PreviewView
    ) {
        if (_isCameraInitialized) return

        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().apply {
                surfaceProvider = previewView.surfaceProvider
            }

            val imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
                _imageCapture = imageCapture
                _isCameraInitialized = true
            } catch (e: Exception) {
                Log.e("CameraX", "Camera setup failed", e)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    fun takePhoto(context: Context) {
        val imageCapture = _imageCapture ?: return

        val photoFile = File(
            context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "visma_${System.currentTimeMillis()}.jpg"
        )

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e("CameraX", "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val uri = Uri.fromFile(photoFile)
                    viewModelScope.launch {
                        withContext(Dispatchers.IO) {
                            uri.path?.let {
                                val result = savePhotoUseCase(photoFile)
                                when (result) {
                                    is VismaResult.Success -> {
                                        photoInfo.value =
                                            Pair(result.result.path.toUri(), result.result.id)
                                        Log.e("PHOTO", "Photo saved to: $uri")
                                    }

                                    is VismaResult.Error -> {
                                        photoInfo.value = Pair(Uri.EMPTY, result.message)
                                        Log.e("PHOTO", "Error saving photo: ${result.message}")
                                    }
                                }
                            }
                        }

                    }
                }
            }
        )
    }


}