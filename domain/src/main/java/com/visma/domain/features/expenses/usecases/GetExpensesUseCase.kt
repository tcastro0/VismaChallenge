package com.visma.domain.features.expenses.usecases

import androidx.paging.PagingData
import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.repository.ExpensesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpensesRepository
) {
    operator fun invoke():  Flow<PagingData<Expense>>{
        return expenseRepository.getPagedExpenses()
    }
}