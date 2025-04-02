package com.visma.domain.features.expenses.models

data class Expense(
    val id: Int,
    val description: String,
    val amount: Double,
    val date: Long,
    val imagePath: String? = null
)