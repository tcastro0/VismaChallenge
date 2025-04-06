package com.visma.data.features.photos.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.visma.data.features.photocapture.models.daos.PhotoDao
import com.visma.data.features.photocapture.models.mappers.toDomain
import com.visma.domain.features.photocapture.models.Photo
import com.visma.domain.features.photos.repository.PhotosRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class PhotosRepositoryImpl @Inject constructor(
    val photosDao: PhotoDao,
) : PhotosRepository {

    override suspend fun getPagedPhotos(): Flow<PagingData<Photo>> {
        return Pager(
            config = PagingConfig(pageSize = 10, initialLoadSize = 10),
            pagingSourceFactory = { photosDao.getPagedPhotos() }
        ).flow.map { pagingData ->
            Log.d("getPagedExpenses", "pagingData: $pagingData")
            pagingData.map { it.toDomain() }
        }
    }

}