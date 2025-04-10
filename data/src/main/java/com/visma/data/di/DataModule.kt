package com.visma.data.di

import android.content.Context
import com.visma.data.features.expenses.models.daos.ExpenseDao
import com.visma.data.features.expenses.repository.ExpenseRepositoryImpl
import com.visma.data.features.photocapture.models.daos.PhotoDao
import com.visma.data.features.photocapture.models.daos.PhotoExpenseDao
import com.visma.data.features.photocapture.repository.PhotoCaptureRepositoryImpl
import com.visma.data.features.photocapture.repository.PhotoExpenseRepositoryImpl
import com.visma.data.features.photos.repository.PhotosRepositoryImpl
import com.visma.domain.features.expenses.repository.ExpensesRepository
import com.visma.domain.features.photocapture.repository.PhotoCaptureRepository
import com.visma.domain.features.photocapture.repository.PhotoExpenseRepository
import com.visma.domain.features.photos.repository.PhotosRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideExpenseRepository(expenseDao: ExpenseDao): ExpensesRepository {
        return ExpenseRepositoryImpl(expenseDao)
    }

    @Provides
    @Singleton
    fun providePhotoCaptureRepository(
        @ApplicationContext context: Context,
        photoDao: PhotoDao
    ): PhotoCaptureRepository {
        return PhotoCaptureRepositoryImpl(context, photoDao)
    }

    @Provides
    @Singleton
    fun providePhotoExpenseRepository(photoExpenseDao: PhotoExpenseDao): PhotoExpenseRepository {
        return PhotoExpenseRepositoryImpl(photoExpenseDao)
    }


    @Provides
    @Singleton
    fun providePhotosRepository(photoDao: PhotoDao): PhotosRepository {
        return PhotosRepositoryImpl(photoDao)
    }
}