package com.visma.data.features.photocapture.models.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.visma.data.features.photocapture.models.entities.PhotoEntity



@Dao
interface PhotoDao {
    @Insert
    suspend fun insertPhoto(photo: PhotoEntity)

    @Query("SELECT * FROM photos ORDER BY timestamp DESC")
    fun getPagedPhotos(): PagingSource<Int, PhotoEntity>
}