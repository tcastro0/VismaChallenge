package com.visma.domain.features.photocapture.usecases

import com.visma.domain.core.VismaResult
import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.photocapture.repository.PhotoExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesByPhotoUseCase @Inject constructor(
    private val photoExpenseRepository: PhotoExpenseRepository
) {
    suspend operator fun invoke(photoId: String): VismaResult<Flow<List<Expense>>> {
        return try {
            val result = photoExpenseRepository.getExpensesByPhoto(photoId)
            VismaResult.Success(result)
        } catch (e: Exception) {
            VismaResult.Error(e.message ?: "Unknown error", cause = e)
        }

    }
}