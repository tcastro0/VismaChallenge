package com.visma.domain.features.photos.usecases

import com.visma.domain.core.VismaResult
import com.visma.domain.features.photocapture.models.Photo
import com.visma.domain.features.photos.repository.PhotosRepository
import javax.inject.Inject


class GetPhotoByIdUseCase @Inject constructor(
    private val photoRepository: PhotosRepository
) {
    suspend operator fun invoke(photoId: String): VismaResult<Photo> {
        return try {
            val result = photoRepository.getPhotoById(photoId)
            if (result != null) {
                return VismaResult.Success(result)

            } else {
                return VismaResult.Error("Photo not found")
            }

        } catch (e: Exception) {
            VismaResult.Error(e.message ?: "Unknown error", cause = e)
        }

    }
}