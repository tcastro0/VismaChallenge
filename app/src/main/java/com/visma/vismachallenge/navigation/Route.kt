package com.visma.vismachallenge.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Route(val route: String) {
    @Serializable
    data object Dashboard : Route("dashboard")
    @Serializable
    data object ExpenseList : Route("expense_list")
    @Serializable
    data object AddExpense : Route("add_expense")
    @Serializable
    data object PhotoCapture : Route("photo_capture")
    @Serializable
    data object Photos : Route("photos")

}