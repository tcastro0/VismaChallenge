package com.visma.expenses.presentation.state

import androidx.paging.PagingData
import com.visma.domain.features.expenses.models.Expense
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ExpenseListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: Flow<PagingData<Expense>> = emptyFlow<PagingData<Expense>>()
)