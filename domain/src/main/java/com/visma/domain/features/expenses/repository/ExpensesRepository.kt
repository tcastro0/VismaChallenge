package com.visma.domain.features.expenses.repository

import androidx.paging.PagingData
import com.visma.domain.features.expenses.models.Expense
import kotlinx.coroutines.flow.Flow


interface ExpensesRepository {
    fun getAllExpenses(): Flow<List<Expense>>
    fun getPagedExpenses(): Flow<PagingData<Expense>>
    suspend fun addExpense(expense: Expense)
    suspend fun deleteExpense(expense: Expense)
}