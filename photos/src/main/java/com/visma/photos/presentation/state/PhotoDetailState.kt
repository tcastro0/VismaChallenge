package com.visma.photos.presentation.state

import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.photocapture.models.Photo



data class PhotoDetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val photo: Photo? = null,
    val detail: Expense? = null
)