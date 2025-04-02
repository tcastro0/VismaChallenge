package com.visma.data.features.expenses.repository

import com.visma.data.features.expenses.models.daos.ExpenseDao
import com.visma.data.features.expenses.models.mappers.toDomain
import com.visma.data.features.expenses.models.mappers.toEntity
import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.repository.ExpensesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepositoryImpl @Inject constructor(
    private val expenseDao: ExpenseDao
) : ExpensesRepository {

    override fun getAllExpenses(): Flow<List<Expense>> {
        return expenseDao.getAllExpenses().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addExpense(expense: Expense) {
        expenseDao.insertExpense(expense.toEntity())
    }

    override suspend fun deleteExpense(expense: Expense) {
        expenseDao.deleteExpense(expense.toEntity())
    }
}