package com.visma.domain.features.expenses.usecases

import com.visma.domain.core.VismaResult
import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.repository.ExpensesRepository
import java.time.LocalDateTime
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpensesRepository
) {
    suspend operator fun invoke(expense: Expense): VismaResult<Unit> {
        return try {
            validateExpense(expense)
            expenseRepository.addExpense(expense)
            VismaResult.Success(Unit)
        } catch (e: Exception) {
            VismaResult.Error(e.message ?: "Unknown error",cause = e)
        }
    }

    private fun validateExpense(expense: Expense) {
        if (expense.amount < 0) {
            throw IllegalArgumentException("Invalid amount")
        }
        if (expense.date.isAfter(LocalDateTime.now())) {
            throw IllegalArgumentException("Invalid date")
        }
        if (expense.description.isBlank()) {
            throw IllegalArgumentException("Invalid description")
        }
        if (expense.imagePath == null) {
            throw IllegalArgumentException("Invalid image")
        }
    }
}