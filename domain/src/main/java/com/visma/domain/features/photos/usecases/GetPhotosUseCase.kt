package com.visma.domain.features.photos.usecases

import androidx.paging.PagingData
import com.visma.domain.features.photocapture.models.Photo
import com.visma.domain.features.photos.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetPhotosUseCase @Inject constructor(
    private val photoRepository: PhotosRepository
) {
    suspend operator fun invoke(): Flow<PagingData<Photo>> {
        return photoRepository.getPagedPhotos()
    }
}