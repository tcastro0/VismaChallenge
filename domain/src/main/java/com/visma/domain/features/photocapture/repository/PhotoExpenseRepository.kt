package com.visma.domain.features.photocapture.repository

import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.photocapture.models.Photo
import com.visma.domain.features.photocapture.models.PhotoExpense
import kotlinx.coroutines.flow.Flow


interface PhotoExpenseRepository {
    suspend fun savePhotoExpense(expenseId: String, photoId: String): PhotoExpense

    suspend fun getPhotosByExpense(expenseId: String): Flow<List<Photo>>
    suspend fun getExpensesByPhoto(photoId: String): Flow<List<Expense>>
}