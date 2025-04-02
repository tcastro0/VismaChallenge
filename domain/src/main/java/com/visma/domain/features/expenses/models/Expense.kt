package com.visma.domain.features.expenses.models

data class Expense(
    val id: String,
    val description: String,
    val amount: Double,
    val date: Long,
    val currency: String,
    val imagePath: String? = null
)