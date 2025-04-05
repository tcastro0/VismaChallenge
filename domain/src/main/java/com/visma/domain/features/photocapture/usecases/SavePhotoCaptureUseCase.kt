package com.visma.domain.features.photocapture.usecases

import com.visma.domain.core.VismaResult
import com.visma.domain.features.photocapture.models.Photo
import com.visma.domain.features.photocapture.repository.PhotoCaptureRepository
import java.io.File
import javax.inject.Inject


class SavePhotoCaptureUseCase @Inject constructor(
    private val photoCaptureRepository: PhotoCaptureRepository
    ) {
    suspend operator fun invoke(imageFile: File): VismaResult<Photo> {
        return try {
            val result = photoCaptureRepository.savePhoto(imageFile)
            VismaResult.Success(result)
        } catch (e: Exception) {
            VismaResult.Error(e.message ?: "Unknown error", cause = e)
        }

    }
}