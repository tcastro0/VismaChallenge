package com.visma.expenses.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpensesTopBar(
    title: String,
    onBackClick: () -> Unit,
    onActionClick: () -> Unit,
    isActionEnabled: Boolean = false
) {
    TopAppBar(
        title = {
            Text(title, style = MaterialTheme.typography.headlineLarge)
        },
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go back"
                )
            }
        },
        actions = {
            IconButton(
                enabled = isActionEnabled,
                onClick = {
                    onActionClick()
                }) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Localized description"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddExpensesTopBarPreview() {
    AddExpensesTopBar(
        "Add Expense",
        onBackClick = { },
        onActionClick = { },
        isActionEnabled = true
    )
}