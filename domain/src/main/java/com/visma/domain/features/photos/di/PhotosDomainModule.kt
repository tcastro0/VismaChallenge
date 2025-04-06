package com.visma.domain.features.photos.di

import com.visma.domain.features.photos.repository.PhotosRepository
import com.visma.domain.features.photos.usecases.GetPhotoByIdUseCase
import com.visma.domain.features.photos.usecases.GetPhotosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object PhotosDomainModule {

    @Provides
    fun provideGetPhotosUseCase(repository: PhotosRepository): GetPhotosUseCase {
        return GetPhotosUseCase(repository)
    }

    @Provides
    fun provideGetPhotoByIdUseCase(repository: PhotosRepository): GetPhotoByIdUseCase {
        return GetPhotoByIdUseCase(repository)
    }
}