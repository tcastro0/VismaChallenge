package com.visma.data.features.photocapture.models.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.visma.data.features.expenses.models.entities.ExpenseEntity
import com.visma.data.features.photocapture.models.entities.PhotoEntity
import com.visma.data.features.photocapture.models.entities.PhotoExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoExpenseDao {
    @Insert
    suspend fun insertPhotoExpense(photoExpense: PhotoExpenseEntity)

    @Transaction
    @Query("SELECT * FROM photos p INNER JOIN photo_expense pe ON p.id = pe.photoID WHERE pe.expenseID = :expenseId")
    suspend fun getPhotosByExpense(expenseId: String): Flow<List<PhotoEntity>>

    @Transaction
    @Query("SELECT * FROM expenses e INNER JOIN photo_expense pe ON e.id = pe.expenseID WHERE pe.photoID = :photoId")
    suspend fun getExpensesByPhoto(photoId: String): Flow<List<ExpenseEntity>>
}