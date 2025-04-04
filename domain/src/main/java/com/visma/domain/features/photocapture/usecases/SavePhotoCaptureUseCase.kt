package com.visma.domain.features.photocapture.usecases

import com.visma.domain.core.VismaResult
import com.visma.domain.features.photocapture.repository.PhotoCaptureRepository
import javax.inject.Inject


class SavePhotoCaptureUseCase @Inject constructor(
    private val photoCaptureRepository: PhotoCaptureRepository
    ) {
    suspend operator fun invoke(imagePath: String): VismaResult {
        return try {
            photoCaptureRepository.savePhoto(imagePath)
            VismaResult.Success
        } catch (e: Exception) {
            VismaResult.Error(e.message ?: "Unknown error", cause = e)
        }

    }
}