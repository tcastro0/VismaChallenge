package com.visma.expenses.presentation.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.visma.domain.features.expenses.models.Expense
import com.visma.expenses.presentation.components.AddExpensesTopBar
import com.visma.expenses.presentation.components.DatePickerComponent
import com.visma.expenses.presentation.components.DropdownSelector
import com.visma.expenses.presentation.viewmodel.AddExpenseViewModel
import io.mockk.mockk
import java.time.LocalDateTime
import androidx.core.net.toUri
import androidx.navigation.NavBackStackEntry
import com.visma.expenses.presentation.components.PhotoPlaceholderComponent
import io.mockk.every

@Composable
fun AddExpenseScreen(
    navController: NavController,
    viewModel: AddExpenseViewModel,
    onBackClick: () -> Unit,
    takePhoto: () -> Unit
) {
    val scrollState = rememberScrollState()
    val state = viewModel.state.collectAsState()
    val expense by remember(state) { derivedStateOf { state.value.expense } }
    val navBackStackEntry = navController.currentBackStackEntry
    
    var showSnackbar by remember { mutableStateOf(false) }
    var showSnackbarError by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }


    navBackStackEntry?.savedStateHandle?.get<String>("resultUri")?.let {
        viewModel.updateExpense(imagePath = it.toUri().toString())
        Log.e("resultUri", it.toUri().toString())
    }
    navBackStackEntry?.savedStateHandle?.get<String>("resultId")?.let {
        viewModel.updateExpense(imageId = it)
        Log.e("resultId", it)
    }

    if (showSnackbar) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("Data saved successfully!")
            onBackClick()
            showSnackbar = false
        }
    }

    if (showSnackbarError) {
        LaunchedEffect(snackbarHostState) {
            snackbarHostState.showSnackbar("Something went wrong")
            showSnackbarError = false
        }
    }
    Scaffold(
        topBar = {
            AddExpensesTopBar(
                "Add Expense",
                onBackClick = { onBackClick.invoke() },
                onActionClick = { viewModel.saveExpense() },
                isActionEnabled = state.value.isSaveEnabled
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }


    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            if (state.value.success) {
                showSnackbar = true
            }
            if (state.value.isLoading) {
                LinearProgressIndicator(
                    modifier =
                        Modifier.fillMaxWidth()
                )
            }
            if (!state.value.error.isNullOrEmpty()) {
                showSnackbarError = true
            }

            ExpenseForm(
                expense,
                viewModel.availableCurrencies,
                updateExpense = { description, amount, date, currency ->
                    viewModel.updateExpense(amount, description, date, currency)
                },
                takePhoto
            )
        }
    }

}

@Composable
fun ExpenseForm(
    expense: Expense,
    availableCurrencies: List<String>,
    updateExpense: (String?, Double?, LocalDateTime?, String?) -> Unit,
    takePhoto: () -> Unit
) {
    var amountText by remember {
        mutableStateOf(expense.amount.toString().takeIf { it != "0.0" } ?: "")
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = expense.description,
            onValueChange = { updateExpense.invoke(it, null, null, null) },
            textStyle = MaterialTheme.typography.bodyMedium,
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = amountText,
                onValueChange = {
                    amountText = it
                    if (it == "-" || it.toDoubleOrNull() != null) {
                        updateExpense.invoke(null, it.toDoubleOrNull(), null, null)
                    }

                },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.weight(1f),
            )
            Box(modifier = Modifier.weight(0.7f)) {
                DropdownSelector("", expense.currency.getCurrency(), availableCurrencies) {
                    updateExpense.invoke(null, null, null, it)
                }
            }

        }


        Spacer(modifier = Modifier.height(16.dp))

        DatePickerComponent(expense.date) { date ->
            updateExpense.invoke(null, null, date, null)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .border(
                    border = BorderStroke(
                        width = 2.dp,
                        brush = SolidColor(Color.Gray)
                    ),
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(16.dp)
                .clickable { takePhoto.invoke() },
            contentAlignment = Alignment.Center
        ) {
            expense.imagePath?.let {
                Image(
                    painter = rememberAsyncImagePainter(it),
                    contentDescription = null,
                    modifier = Modifier.height(200.dp).fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            } ?: run {
                PhotoPlaceholderComponent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddExpenseScreenPreview() {
    val mockNavController = mockk<NavController>()
    val mockBackStackEntry = mockk<NavBackStackEntry>()
    every { mockNavController.currentBackStackEntry } returns mockBackStackEntry
    AddExpenseScreen(
        navController = mockNavController,
        viewModel = AddExpenseViewModel(mockk(), mockk()),
        onBackClick = {},
        takePhoto = {})
}