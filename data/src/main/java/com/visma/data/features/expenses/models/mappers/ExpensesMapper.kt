package com.visma.data.features.expenses.models.mappers

import com.visma.data.features.expenses.models.entities.ExpenseEntity
import com.visma.domain.features.expenses.models.Expense

fun ExpenseEntity.toDomain(): Expense {
    return Expense(
        id = this.id,
        description = this.description,
        amount = this.amount,
        date = this.date,
        imagePath = this.imagePath,
        currency = this.currency,
    )
}

fun Expense.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = this.id,
        description = this.description,
        amount = this.amount,
        date = this.date,
        imagePath = this.imagePath,
        currency = this.currency,
    )
}