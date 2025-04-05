package com.visma.domain.features.photocapture.usecases

import com.visma.domain.core.VismaResult
import com.visma.domain.features.photocapture.models.Photo
import com.visma.domain.features.photocapture.repository.PhotoCaptureRepository
import javax.inject.Inject


class SavePhotoExpenseUseCase @Inject constructor(
    private val photoCaptureRepository: PhotoCaptureRepository
) {
    suspend operator fun invoke(imagePath: String): VismaResult<Photo> {
        return try {
            val result = photoCaptureRepository.savePhoto(imagePath)
            VismaResult.Success(result)
        } catch (e: Exception) {
            VismaResult.Error(e.message ?: "Unknown error", cause = e)
        }

    }
}