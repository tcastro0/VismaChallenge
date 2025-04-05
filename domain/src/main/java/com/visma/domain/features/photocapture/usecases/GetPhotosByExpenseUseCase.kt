package com.visma.domain.features.photocapture.usecases

import com.visma.domain.core.VismaResult
import com.visma.domain.features.photocapture.models.Photo
import com.visma.domain.features.photocapture.repository.PhotoExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject



class GetPhotosByExpenseUseCase @Inject constructor(
    private val photoExpenseRepository: PhotoExpenseRepository
) {
    suspend operator fun invoke(expenseId: String): VismaResult<Flow<List<Photo>>> {
        return try {
            val result = photoExpenseRepository.getPhotosByExpense(expenseId)
            VismaResult.Success(result)
        } catch (e: Exception) {
            VismaResult.Error(e.message ?: "Unknown error", cause = e)
        }

    }
}