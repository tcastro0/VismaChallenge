package com.visma.domain.features.expenses.repository

import com.visma.domain.features.expenses.models.Expense
import kotlinx.coroutines.flow.Flow


interface ExpensesRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    suspend fun addExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
}