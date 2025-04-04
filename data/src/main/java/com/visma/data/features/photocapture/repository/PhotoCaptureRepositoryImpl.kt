package com.visma.data.features.photocapture.repository

import android.content.Context
import com.visma.domain.features.photocapture.repository.PhotoCaptureRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class PhotoCaptureRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PhotoCaptureRepository {
    override suspend fun savePhoto(imagePath: String) {

    }
}