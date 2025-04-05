package com.visma.domain.features.photocapture.usecases

import com.visma.domain.core.VismaResult
import com.visma.domain.features.photocapture.models.PhotoExpense
import com.visma.domain.features.photocapture.repository.PhotoExpenseRepository
import javax.inject.Inject


class SavePhotoExpenseUseCase @Inject constructor(
    private val photoExpenseRepository: PhotoExpenseRepository
) {
    suspend operator fun invoke(expenseId: String, photoId: String): VismaResult<PhotoExpense> {
        return try {
            val result = photoExpenseRepository.savePhotoExpense(expenseId, photoId)
            VismaResult.Success(result)
        } catch (e: Exception) {
            VismaResult.Error(e.message ?: "Unknown error", cause = e)
        }

    }
}