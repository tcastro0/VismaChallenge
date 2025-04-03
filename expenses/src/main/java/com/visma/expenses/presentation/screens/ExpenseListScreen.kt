package com.visma.expenses.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.visma.domain.features.expenses.models.Expense
import com.visma.expenses.presentation.components.EmptyListComponent
import com.visma.expenses.presentation.components.ExpenseAddFabComponent
import com.visma.expenses.presentation.state.ExpenseListState
import com.visma.expenses.presentation.viewmodel.ExpenseListViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel,
    onAddExpenseClick: () -> Unit,
    fab: (@Composable () -> Unit) -> Unit
) {
    val state = viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        fab {
            ExpenseAddFabComponent(onAddExpenseClick)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (state.value.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        } else if (!state.value.error.isNullOrEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Warning, "warning")
                Text(text = "${state.value.error}")
            }
        } else if (state.value.data.isNotEmpty()) {
            val expenses = state.value.data
            LazyColumn {
                items(expenses.size, key = null) { index ->
                    Text(expenses[index].description)
                }
            }
        }else {
            EmptyListComponent()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExpenseScreenPreview() {
    val mockViewModel = mockk<ExpenseListViewModel>()
    val state = MutableStateFlow(
        ExpenseListState(
            isLoading = false, data = listOf(
                Expense(
                    id = "Lunch",
                    description = "Lunch",
                    amount = 12.00,
                    date = System.currentTimeMillis(),
                    currency = "EUR"
                ), Expense(
                    id = "Coffee",
                    description = "Coffee",
                    amount = 1.00,
                    date = System.currentTimeMillis(),
                    currency = "USD"
                )
            )
        )
    )
    every { mockViewModel.state } returns state
    ExpenseListScreen(mockViewModel, {}, {})
}


@Preview(showBackground = true)
@Composable
fun ExpenseScreenLoadingPreview() {
    val mockViewModel = mockk<ExpenseListViewModel>()
    every { mockViewModel.state } returns MutableStateFlow(
        ExpenseListState(isLoading = true)
    )
    ExpenseListScreen(mockViewModel, {}, {})
}


@Preview(showBackground = true)
@Composable
fun ExpenseScreenErrorPreview() {
    val mockViewModel = mockk<ExpenseListViewModel>()
    every { mockViewModel.state } returns MutableStateFlow(
        ExpenseListState(error = "Something went wrong")
    )

    ExpenseListScreen(mockViewModel, {}, {})
}