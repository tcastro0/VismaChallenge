package com.visma.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.visma.data.features.expenses.models.daos.ExpenseDao
import com.visma.data.features.expenses.models.entities.ExpenseEntity


@Database(
    entities = [
        ExpenseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VismaDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}