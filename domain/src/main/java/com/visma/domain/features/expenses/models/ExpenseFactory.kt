package com.visma.domain.features.expenses.models

import java.time.LocalDateTime
import java.util.UUID



class ExpensesFactory {
    companion object{
        fun createEmpty(): Expense {
            return Expense(
                id = UUID.randomUUID().toString(),
                amount = 0.0,
                description = "",
                date = LocalDateTime.now(),
                currency = VismaCurrency.EUR,
            )
        }
    }

}