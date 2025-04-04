package com.visma.data.features.expenses.models.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val description: String,
    val amount: Double,
    val date: Long,
    val currency: String,
    val imagePath: String? = null
)
