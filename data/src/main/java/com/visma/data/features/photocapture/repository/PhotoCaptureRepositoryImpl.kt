package com.visma.data.features.photocapture.repository

import android.content.Context
import com.visma.data.features.photocapture.models.daos.PhotoDao
import com.visma.data.features.photocapture.models.mappers.toEntity
import com.visma.domain.features.photocapture.models.Photo
import com.visma.domain.features.photocapture.repository.PhotoCaptureRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import javax.inject.Inject


class PhotoCaptureRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    val photoDao: PhotoDao
) : PhotoCaptureRepository {
    override suspend fun savePhoto(file: File): Photo = withContext(Dispatchers.IO) {
        val destinationDir = File(context.filesDir, "photos")
        if (!destinationDir.exists()) destinationDir.mkdirs()

        val imageFile = File(destinationDir, "${System.currentTimeMillis()}.jpg")
        val inputStream = file.inputStream()
        val outputStream = FileOutputStream(imageFile)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        val photo = Photo(
            id = UUID.randomUUID().toString(),
            filename = imageFile.name,
            path = imageFile.absolutePath
        )

        photoDao.insertPhoto(photo.toEntity())
        return@withContext photo
    }
}