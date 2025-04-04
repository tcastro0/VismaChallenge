package com.visma.vismachallenge.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {
    @Serializable
    object ExpenseList : Route("expense_list")
    @Serializable
    object AddExpense : Route("add_expense")

}