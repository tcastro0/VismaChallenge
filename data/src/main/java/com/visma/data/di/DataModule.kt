package com.visma.data.di

import com.visma.data.features.expenses.models.daos.ExpenseDao
import com.visma.data.features.expenses.repository.ExpenseRepositoryImpl
import com.visma.domain.features.expenses.repository.ExpensesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideExpenseRepository(expenseDao: ExpenseDao): ExpensesRepository {
        return ExpenseRepositoryImpl(expenseDao)
    }
}