package com.visma.data.features.expenses.models.daos

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.visma.data.features.expenses.models.entities.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>


    @Query("SELECT * FROM expenses ORDER BY date DESC")
    fun getPagedExpenses(): PagingSource<Int, ExpenseEntity>

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
}