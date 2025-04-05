package com.visma.data.features.photocapture.models.daos

import androidx.room.Dao
import androidx.room.Insert
import com.visma.data.features.photocapture.models.entities.PhotoEntity



@Dao
interface PhotoDao {
    @Insert
    suspend fun insertPhoto(photo: PhotoEntity)
}