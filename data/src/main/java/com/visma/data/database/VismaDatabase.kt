package com.visma.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.visma.data.features.expenses.models.daos.ExpenseDao
import com.visma.data.features.expenses.models.entities.ExpenseEntity
import com.visma.data.features.photocapture.models.daos.PhotoExpenseDao
import com.visma.data.features.photocapture.models.entities.PhotoEntity
import com.visma.data.features.photocapture.models.entities.PhotoExpenseEntity


@Database(
    entities = [
        ExpenseEntity::class,
        PhotoEntity::class,
        PhotoExpenseEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class VismaDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
    abstract fun photoExpenseDao(): PhotoExpenseDao

}