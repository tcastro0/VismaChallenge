package com.visma.expenses.presentation.state

import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.models.ExpensesFactory


data class AddExpenseState(
    val expense: Expense = ExpensesFactory.createEmpty(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
) {
    val isSaveEnabled: Boolean
        get() = expense.amount > 0 &&
                expense.description.isNotEmpty()
}
