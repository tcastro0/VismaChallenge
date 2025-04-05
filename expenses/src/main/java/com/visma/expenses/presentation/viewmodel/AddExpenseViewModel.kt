package com.visma.expenses.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.visma.domain.core.VismaResult
import com.visma.domain.features.expenses.models.VismaCurrency
import com.visma.domain.features.expenses.usecases.AddExpenseUseCase
import com.visma.domain.features.photocapture.usecases.SavePhotoExpenseUseCase
import com.visma.expenses.presentation.state.AddExpenseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpenseUseCase: AddExpenseUseCase,
    private val savePhotoExpenseUseCase: SavePhotoExpenseUseCase
) : ViewModel() {
    var state = MutableStateFlow<AddExpenseState>(AddExpenseState(isLoading = false))
        private set

    val availableCurrencies = VismaCurrency.entries.map { it.getCurrency() }

    fun saveExpense() {
        viewModelScope.launch {
            state.value = state.value.copy(isLoading = true)
            delay(500)

            val result = addExpenseUseCase(state.value.expense)
            when (result) {
                is VismaResult.Success -> {
                    state.value = state.value.copy(isLoading = false, success = true)
                    saveExpensePhotoRelation(state.value.expense.id, state.value.expense.imageId!!)
                }

                is VismaResult.Error -> {
                    state.value = state.value.copy(isLoading = false, error = result.message)
                }
            }

        }
    }

    suspend fun saveExpensePhotoRelation(expenseId: String, photoId: String) {
        val result = savePhotoExpenseUseCase(expenseId, photoId)
        when (result) {
            is VismaResult.Success -> {
                state.value = state.value.copy(isLoading = false, success = true)
            }

            is VismaResult.Error -> {
                state.value = state.value.copy(isLoading = false, error = result.message)
            }
        }
    }

    fun updateExpense(
        amount: Double? = null,
        description: String? = null,
        date: LocalDateTime? = null,
        currency: String? = null,
        imagePath: String? = null,
        imageId: String? = null,
    ) {
        val currentExpense = state.value.expense
        val newExpense = currentExpense.copy(
            description = description ?: currentExpense.description,
            amount = amount ?: currentExpense.amount,
            date = date ?: currentExpense.date,
            currency = if (currency != null) VismaCurrency.fromCurrency(currency) else currentExpense.currency,
            imagePath = imagePath ?: currentExpense.imagePath,
            imageId = imageId ?: currentExpense.imageId,
        )

        if (newExpense != currentExpense) {
            state.value = state.value.copy(expense = newExpense)
        }
    }


}