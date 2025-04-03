package com.visma.domain.features.expenses.di

import com.visma.domain.features.expenses.repository.ExpensesRepository
import com.visma.domain.features.expenses.usecases.AddExpenseUseCase
import com.visma.domain.features.expenses.usecases.GetExpensesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    fun provideAddExpenseUseCase(expenseRepository: ExpensesRepository): AddExpenseUseCase {
        return AddExpenseUseCase(expenseRepository)
    }

    @Provides
    fun provideGetExpensesUseCase(expenseRepository: ExpensesRepository): GetExpensesUseCase {
        return GetExpensesUseCase(expenseRepository)
    }
}