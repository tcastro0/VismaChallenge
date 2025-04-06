package com.visma.expenses.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.models.VismaCurrency
import com.visma.expenses.presentation.components.EmptyListComponent
import com.visma.expenses.presentation.components.ExpenseAddFabComponent
import com.visma.expenses.presentation.components.ExpensiveListItemComponent
import com.visma.expenses.presentation.state.ExpenseListState
import com.visma.expenses.presentation.viewmodel.ExpenseListViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime

@Composable
fun ExpenseListScreen(
    viewModel: ExpenseListViewModel,
    onAddExpenseClick: () -> Unit,
    fab: (@Composable () -> Unit) -> Unit
) {
    val state = viewModel.state.collectAsState()
    val expenses = state.value.data.collectAsLazyPagingItems()


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
        } else if (expenses.itemCount > 0) {


            LazyColumn {
                items(expenses.itemCount) { index ->
                    expenses[index]?.let { ExpensiveListItemComponent(expense = it) {} }
                }
                expenses.apply {
                    when {
                        loadState.refresh is LoadState.Loading ||
                                loadState.append is LoadState.Loading -> {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        loadState.refresh is LoadState.Error ||
                                loadState.append is LoadState.Error -> {
                            val error = (loadState.refresh as LoadState.Error).error
                            item {
                                Text(
                                    text = "Error: ${error.message}",
                                    color = MaterialTheme.colorScheme.error,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                )
                            }
                        }

                    }
                }

            }
        } else {
            EmptyListComponent()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ExpenseScreenPreview() {
    val mockViewModel = mockk<ExpenseListViewModel>()
    val list = listOf(
        Expense(
            id = "Lunch",
            description = "Lunch",
            amount = 12.00,
            date = LocalDateTime.now(),
            currency = VismaCurrency.USD
        ), Expense(
            id = "Coffee",
            description = "Coffee",
            amount = 1.00,
            date = LocalDateTime.now(),
            currency = VismaCurrency.EUR
        )
    )

    val state = MutableStateFlow(
        ExpenseListState(
            isLoading = false, data = mockk()
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