package com.visma.photos.presentation.state

import androidx.paging.PagingData
import com.visma.domain.features.photocapture.models.Photo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow


data class PhotosScreenState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: Flow<PagingData<Photo>> = emptyFlow<PagingData<Photo>>()
)