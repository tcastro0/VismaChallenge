package com.visma.expenses.presentation.state

import com.visma.domain.features.expenses.models.Expense

data class ExpenseListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val data: List<Expense> = listOf()
)