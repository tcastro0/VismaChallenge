package com.visma.data.features.photocapture.models.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.visma.data.features.expenses.models.entities.ExpenseEntity


@Entity(
    tableName = "photo_expense",
    primaryKeys = ["photoID", "expenseID"],
    foreignKeys = [
        ForeignKey(entity = PhotoEntity::class, parentColumns = ["id"], childColumns = ["photoID"]),
        ForeignKey(entity = ExpenseEntity::class, parentColumns = ["id"], childColumns = ["expenseID"])
    ],
    indices = [
        Index(value = ["photoID"]),
        Index(value = ["expenseID"]),
        Index(value = ["photoID", "expenseID"])
    ]
)
data class PhotoExpenseEntity(
    val photoID: String,
    val expenseID: String
)