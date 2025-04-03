package com.visma.data.di

import android.content.Context
import androidx.room.Room
import com.visma.data.database.VismaDatabase
import com.visma.data.features.expenses.models.daos.ExpenseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VismaDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            VismaDatabase::class.java,
            "visma_database"
        ).build()
    }

    @Provides
    fun provideExpenseDao(db: VismaDatabase): ExpenseDao {
        return db.expenseDao()
    }
}