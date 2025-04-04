package com.visma.domain.features.expenses.models

import java.time.LocalDateTime

data class Expense(
    val id: String,
    val description: String,
    val amount: Double,
    val date: LocalDateTime,
    val currency: VismaCurrency,
    val imagePath: String? = null
)