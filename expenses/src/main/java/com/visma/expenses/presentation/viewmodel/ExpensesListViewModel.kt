package com.visma.expenses.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visma.domain.features.expenses.usecases.GetExpensesUseCase
import com.visma.expenses.presentation.state.ExpenseListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val getExpensesUseCase: GetExpensesUseCase
) : ViewModel() {

    var state = MutableStateFlow<ExpenseListState>(ExpenseListState(isLoading = true))
        private set


    init {
        loadExpenses()
    }

    private fun loadExpenses() {
        viewModelScope.launch {
            getExpensesUseCase().catch {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }.collect { list ->
                state.value = state.value.copy(isLoading = false, data = list)
            }
        }
    }
}