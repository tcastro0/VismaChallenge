package com.visma.domain.features.photocapture.di

import com.visma.domain.features.photocapture.repository.PhotoExpenseRepository
import com.visma.domain.features.photocapture.usecases.GetExpensesByPhotoUseCase
import com.visma.domain.features.photocapture.usecases.GetPhotosByExpenseUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object PhotoCaptureDomainModule {

    @Provides
    fun provideGetExpensesByPhotoUseCase(repository: PhotoExpenseRepository): GetExpensesByPhotoUseCase {
        return GetExpensesByPhotoUseCase(repository)
    }

    @Provides
    fun provideGetPhotosByExpenseUseCase(repository: PhotoExpenseRepository): GetPhotosByExpenseUseCase {
        return GetPhotosByExpenseUseCase(repository)
    }
}