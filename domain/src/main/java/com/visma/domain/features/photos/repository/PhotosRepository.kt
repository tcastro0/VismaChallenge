package com.visma.domain.features.photos.repository

import androidx.paging.PagingData
import com.visma.domain.features.photocapture.models.Photo
import kotlinx.coroutines.flow.Flow


interface PhotosRepository {
    suspend fun getPagedPhotos(): Flow<PagingData<Photo>>
    suspend fun getPhotoById(id: String): Photo?
}