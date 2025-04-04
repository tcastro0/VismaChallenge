package com.visma.domain.features.photocapture.repository


interface PhotoCaptureRepository {
    suspend fun savePhoto(imagePath: String)
}