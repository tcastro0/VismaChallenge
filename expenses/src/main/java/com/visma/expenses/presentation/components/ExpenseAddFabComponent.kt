package com.visma.expenses.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ExpenseAddFabComponent(onAddExpenseClick: () -> Unit) {
    FloatingActionButton(
        onClick = onAddExpenseClick,
        shape = CircleShape,
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add Expense")
    }
}