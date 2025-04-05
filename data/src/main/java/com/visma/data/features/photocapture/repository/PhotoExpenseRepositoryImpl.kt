package com.visma.data.features.photocapture.repository

import com.visma.data.features.expenses.models.mappers.toDomain
import com.visma.data.features.photocapture.models.daos.PhotoExpenseDao
import com.visma.data.features.photocapture.models.mappers.toDomain
import com.visma.data.features.photocapture.models.mappers.toEntity
import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.photocapture.models.Photo
import com.visma.domain.features.photocapture.models.PhotoExpense
import com.visma.domain.features.photocapture.repository.PhotoExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject


class PhotoExpenseRepositoryImpl @Inject constructor(
    val photoExpenseDao: PhotoExpenseDao
) : PhotoExpenseRepository {

    override suspend fun savePhotoExpense(expenseId: String, photoId: String): PhotoExpense {
        val photoExpense = PhotoExpense(
            id = UUID.randomUUID().toString(),
            expenseId = expenseId,
            photoId = photoId
        )
        photoExpenseDao.insertPhotoExpense(photoExpense.toEntity())
        return photoExpense
    }

    override suspend fun getPhotosByExpense(expenseId: String): Flow<List<Photo>> {
        return photoExpenseDao.getPhotosByExpense(expenseId)
            .map { photos -> photos.map { it.toDomain() } }
    }

    override suspend fun getExpensesByPhoto(photoId: String): Flow<List<Expense>> {
        return photoExpenseDao.getExpensesByPhoto(photoId)
            .map { expenses -> expenses.map { it.toDomain() } }
    }
}