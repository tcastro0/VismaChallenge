package com.visma.domain.features.expenses.usecases

import com.visma.domain.core.VismaResult
import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.models.VismaCurrency
import com.visma.domain.features.expenses.repository.ExpensesRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import java.time.LocalDateTime
import kotlin.test.Test

class AddExpenseUseCaseTest {

    private lateinit var addExpenseUseCase: AddExpenseUseCase
    private val expenseRepository: ExpensesRepository = mockk()

    private var dateNow: LocalDateTime = LocalDateTime.now()


    @Before
    fun setUp() {
        addExpenseUseCase = AddExpenseUseCase(expenseRepository)
        coEvery { expenseRepository.addExpense(any()) } just Runs
    }

    @Test
    fun `add expense`() = runTest {
        val expense = Expense(
            id = "test1",
            description = "Coffee",
            amount = 3.50,
            date = dateNow,
            currency = VismaCurrency.EUR,
            imagePath = "path"
        )

        addExpenseUseCase(expense)

        coVerify(exactly = 1) { expenseRepository.addExpense(expense) }
    }


    @Test
    fun `throws exception when repository fails`() = runTest {
        val expense = Expense(
            id = "teste throw",
            description = "Dinner",
            amount = 15.00,
            date = dateNow,
            currency = VismaCurrency.EUR,
            imagePath = "path"
        )
        coEvery { expenseRepository.addExpense(any()) } throws RuntimeException("Database Error")

        val result =  addExpenseUseCase(expense)

        assert(result is VismaResult.Error)
        val castResult = result as VismaResult.Error
        Assert.assertEquals("Database Error",castResult.message)
    }


    @Test
    fun `does not add expense if amount is negative`() = runTest {
        val expense = Expense(
            id = "teste invalid amount",
            description = "Invalid",
            amount = -5.00,
            date = dateNow,
            currency = VismaCurrency.EUR,
        )


          val result =  addExpenseUseCase(expense)


        coVerify(exactly = 0) { expenseRepository.addExpense(any()) }

        assert(result is VismaResult.Error)
        val castResult = result as VismaResult.Error
        Assert.assertEquals("Invalid amount",castResult.message)

    }

    @Test
    fun `does not add expense if date is in future`() = runTest {
        val expense = Expense(
            id = "teste invalid date",
            description = "Invalid",
            amount = 1.0,
            date = LocalDateTime.now().plusDays(1) ,
            currency = VismaCurrency.EUR,
            imagePath = "path"
        )

        val result =  addExpenseUseCase(expense)


        coVerify(exactly = 0) { expenseRepository.addExpense(any()) }

        assert(result is VismaResult.Error)
        val castResult = result as VismaResult.Error
        Assert.assertEquals("Invalid date", castResult.message)
    }

    @Test
    fun `multiple expenses are added same time`() = runTest {
        val expenses = listOf(
            Expense(
                "1",
                "Coffee",
                3.50,
                currency = VismaCurrency.EUR,
                date = LocalDateTime.now().plusDays(-1),
                        imagePath = "path"
            ),
            Expense(
                "2",
                "Lunch",
                12.00,
                currency = VismaCurrency.EUR,
                date = LocalDateTime.now().plusDays(-3),
                imagePath = "path"
            ),
            Expense(
                "3",
                "Groceries",
                50.00,
                currency = VismaCurrency.EUR,
                date = LocalDateTime.now(),
                imagePath = "path"
            )
        )

        coroutineScope {
            expenses.map { expense ->
                async(Dispatchers.Default) { addExpenseUseCase(expense) }
            }.awaitAll()
        }

        coVerify(exactly = 3) { expenseRepository.addExpense(any()) }
    }


}