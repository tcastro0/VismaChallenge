package com.visma.data.features.expenses.models.mappers

import com.visma.data.features.expenses.models.entities.ExpenseEntity
import com.visma.domain.features.expenses.models.VismaCurrency
import com.visma.domain.features.expenses.models.Expense
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun ExpenseEntity.toDomain(): Expense {
    return Expense(
        id = this.id,
        description = this.description,
        amount = this.amount,
        date = LocalDateTime.ofInstant(Instant.ofEpochMilli(this.date), ZoneOffset.UTC),
        imagePath = null,
        currency = VismaCurrency.fromCurrency(this.currency),
    )
}

fun Expense.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        id = this.id,
        description = this.description,
        amount = this.amount,
        date = this.date.toInstant(ZoneOffset.UTC).toEpochMilli(),

        currency = this.currency.getCurrency(),
    )
}

