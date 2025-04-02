package com.visma.domain.features.expenses.usecases

import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.repository.ExpensesRepository
import java.time.LocalDateTime
import java.time.ZoneOffset
import javax.inject.Inject

class AddExpenseUseCase @Inject constructor(
    private val expenseRepository: ExpensesRepository
) {
    suspend operator fun invoke(expense: Expense) {
        validateExpense(expense)
        expenseRepository.addExpense(expense)
    }

    private fun validateExpense(expense: Expense) {
         if (expense.amount < 0) {
             throw IllegalArgumentException("Invalid amount")
         }
         if (expense.date > LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)) {
             throw IllegalArgumentException("Invalid date")
         }
    }
}