package com.visma.expenses.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.visma.domain.features.expenses.usecases.GetExpensesUseCase
import com.visma.expenses.presentation.state.ExpenseListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
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
            val flow =  getExpensesUseCase().cachedIn(viewModelScope).catch {
                state.value = state.value.copy(isLoading = false, error = it.message)
            }

            state.update {
                it.copy(isLoading = false, data = flow)
            }
        }
    }
}