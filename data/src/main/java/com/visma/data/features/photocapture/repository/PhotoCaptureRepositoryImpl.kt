package com.visma.data.features.photocapture.repository

import android.content.Context
import androidx.core.net.toUri
import com.visma.domain.features.photocapture.repository.PhotoCaptureRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class PhotoCaptureRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PhotoCaptureRepository {
    override suspend fun savePhoto(imagePath: String) {
        withContext(Dispatchers.IO) {
            val file = File(context.filesDir, "photos")
            if (!file.exists()) file.mkdirs()

            val imageFile = File(file, "${System.currentTimeMillis()}.jpg")
            val inputStream = context.contentResolver.openInputStream(imagePath.toUri())
            val outputStream = FileOutputStream(imageFile)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
        }
    }
}