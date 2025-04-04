package com.visma.domain.features.expenses.usecases

import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.repository.ExpensesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpensesRepository
) {
    operator fun invoke(): Flow<List<Expense>> {
        return expenseRepository.getAllExpenses()
    }
}