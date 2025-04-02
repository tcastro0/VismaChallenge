package com.visma.domain.features.expenses.usecases

import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.repository.ExpensesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import kotlin.test.assertFailsWith
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

class GetExpensesUseCaseTest {

    private lateinit var getExpensesUseCase: GetExpensesUseCase
    private val expenseRepository: ExpensesRepository = mockk()

    @Before
    fun setUp() {
        getExpensesUseCase = GetExpensesUseCase(expenseRepository)
    }

    @Test
    fun `Successful expense retrieval`() = runTest {
        val expenses = listOf(
            Expense(
                id = UUID.randomUUID().toString(),
                description = "Lunch",
                amount = 12.00,
                date = LocalDateTime.now().toEpochSecond(
                    ZoneOffset.UTC
                ),
                currency = "EUR"
            ),
            Expense(
                id = UUID.randomUUID().toString(),
                description = "Transport",
                amount = 2.50,
                date = LocalDateTime.now().minusDays(2).toEpochSecond(
                    ZoneOffset.UTC
                ),
                currency = "USD"
            )
        )
        coEvery { expenseRepository.getAllExpenses() } returns flowOf(expenses)

        val result = getExpensesUseCase().toList()

        Assert.assertEquals(expenses, result.first())
    }

    @Test
    fun `Empty expense list retrieval`() = runTest {
        coEvery { expenseRepository.getAllExpenses() } returns flowOf(emptyList())
        val result = getExpensesUseCase().toList()

        assert(result.first().isEmpty())
    }

    @Test
    fun `Repository error handling`() = runTest {
        val mockedError = "mapping error"
        coEvery { expenseRepository.getAllExpenses() } returns flow {
            throw ClassCastException(
                mockedError
            )
        }

        val exception = assertFailsWith<ClassCastException> {
            getExpensesUseCase().toList()
        }

        Assert.assertEquals(mockedError, exception.message)
    }

}