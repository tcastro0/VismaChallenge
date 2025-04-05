package com.visma.domain.features.photocapture.repository

import com.visma.domain.features.photocapture.models.Photo


interface PhotoCaptureRepository {
    suspend fun savePhoto(imagePath: String): Photo
}