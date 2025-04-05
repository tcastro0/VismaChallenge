package com.visma.photocapture.presentation.screens


import android.net.Uri
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.visma.photocapture.presentation.components.CameraPermissionHandlerComponent
import com.visma.photocapture.presentation.viewmodel.PhotoCaptureViewModel

@Composable
fun PhotoCaptureScreen(viewModel: PhotoCaptureViewModel, onBackClick: (Uri?) -> Unit) {
    CameraPermissionHandlerComponent  (
        onPermissionsGranted = {
            PhotoCaptureScreenContent(viewModel,onBackClick)
        }

    )
}

 @Composable
 fun PhotoCaptureScreenContent(viewModel: PhotoCaptureViewModel,onBackClick: (Uri) -> Unit){
     val context = LocalContext.current
     val lifecycleOwner = LocalLifecycleOwner.current
     val photoUri by viewModel.photoUri

     val previewView = remember { PreviewView(context) }

     LaunchedEffect(Unit) {
         viewModel.setupCameraIfNeeded(lifecycleOwner, context, previewView)
     }

     Box(modifier = Modifier.fillMaxSize()) {
         AndroidView(factory = { previewView }, modifier = Modifier.fillMaxSize())

         FloatingActionButton(
             onClick = { viewModel.takePhoto(context) },
             modifier = Modifier
                 .align(Alignment.BottomCenter)
                 .padding(16.dp)
         ) {
             Icon(Icons.Default.CameraAlt, contentDescription = "Take Photo")
         }
     }

     photoUri?.let {
         Image(
             painter = rememberAsyncImagePainter(it),
             contentDescription = null,
             modifier = Modifier.size(200.dp)
         )
         onBackClick(it)
     }
 }