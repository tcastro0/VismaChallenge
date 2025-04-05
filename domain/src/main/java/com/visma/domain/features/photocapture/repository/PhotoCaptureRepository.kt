package com.visma.domain.features.photocapture.repository

import com.visma.domain.features.photocapture.models.Photo
import java.io.File


interface PhotoCaptureRepository {
    suspend fun savePhoto(file: File): Photo
}