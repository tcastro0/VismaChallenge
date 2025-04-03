package com.visma.vismachallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.visma.expenses.presentation.screens.ExpenseListScreen
import com.visma.expenses.presentation.viewmodel.ExpenseListViewModel
import com.visma.vismachallenge.ui.theme.VismaChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen() {
    var fabContent by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    val expensesViewModel: ExpenseListViewModel = hiltViewModel()
    VismaChallengeTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            floatingActionButton = { fabContent?.invoke() },
        ) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                ExpenseListScreen(
                    expensesViewModel,
                    onAddExpenseClick = { },
                    fab = { fabContent = it },
                )
            }
        }
    }
}
