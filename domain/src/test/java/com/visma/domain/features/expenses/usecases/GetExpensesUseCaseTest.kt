package com.visma.domain.features.expenses.usecases

import androidx.paging.PagingData
import androidx.paging.map
import com.visma.domain.features.expenses.models.Expense
import com.visma.domain.features.expenses.models.VismaCurrency
import com.visma.domain.features.expenses.repository.ExpensesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.util.UUID

class GetExpensesUseCaseTest {

    private lateinit var getExpensesUseCase: GetExpensesUseCase
    private val expenseRepository: ExpensesRepository = mockk()

    @Before
    fun setUp() {
        getExpensesUseCase = GetExpensesUseCase(expenseRepository)
    }

    /* @Test
     fun `Successful expense retrieval`() = runTest {
         val expenses = listOf(
             Expense(
                 id = UUID.randomUUID().toString(),
                 description = "Lunch",
                 amount = 12.00,
                 date = LocalDateTime.now(),
                 currency = VismaCurrency.USD
             ),
             Expense(
                 id = UUID.randomUUID().toString(),
                 description = "Transport",
                 amount = 2.50,
                 date = LocalDateTime.now().minusDays(2),
                 currency = VismaCurrency.EUR
             )
         )
         coEvery { expenseRepository.getAllExpenses() } returns flowOf(expenses)

         val result = getExpensesUseCase().toList()

         Assert.assertEquals(expenses, result.first())
     }

     @Test
     fun `Empty expense list retrieval`() = runTest {
         coEvery { expenseRepository.getAllExpenses() } returns flowOf(emptyList())
         val result = getExpensesUseCase().first()

         // assert(result.map {  })
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
     }*/

    @Test
    fun `test paging data is emitted correctly`() = runTest {
        val nrEntries = 10000
        coEvery { expenseRepository.getPagedExpenses() } returns flow {
            emit(PagingData.from((1..nrEntries).map {
                Expense(
                    id = UUID.randomUUID().toString(),
                    description = "Expense $it",
                    amount = it.toDouble(),
                    date = LocalDateTime.now(),
                    currency = VismaCurrency.USD
                )
            }))
        }

        val allItems = mutableListOf<Expense>()
        val collected = hashMapOf<Int, MutableList<Expense>>()
        getExpensesUseCase.invoke().collectIndexed{ index, value ->
            value .map { expense ->
                 allItems.add(expense)
                 if (collected.contains(index)) {
                    collected[index]?.addLast(expense) ?: Unit
                } else {
                      collected[index] = arrayListOf(expense)
                }
             }
        }



        assert(allItems.isNotEmpty())
       Assert.assertEquals(2, collected.keys)
          Assert.assertEquals("Expense 501", collected[500])
    }

    @Test
    fun `test pagination`() = runTest {
        val nrEntries = 50

        val firstPage = PagingData.from((1..nrEntries).map {
            Expense(
                id = UUID.randomUUID().toString(),
                description = "Expense $it",
                amount = it.toDouble(),
                date = LocalDateTime.now(),
                currency = VismaCurrency.USD
            )
        })
        val secondPage = PagingData.from((nrEntries..nrEntries * 2).map {
            Expense(
                id = UUID.randomUUID().toString(),
                description = "Expense $it",
                amount = it.toDouble(),
                date = LocalDateTime.now(),
                currency = VismaCurrency.USD
            )
        })


        coEvery { expenseRepository.getPagedExpenses() } returns flow {
            emit(firstPage)
            emit(secondPage)
        }


        val firstPageItems = mutableListOf<Expense>()
        val secondPageItems = mutableListOf<Expense>()
        getExpensesUseCase.invoke().collectIndexed { index, value ->
            if (index == 0) {
                value.map { expense ->
                    firstPageItems.add(expense)

                }
            } else {
                value.map { expense ->
                    secondPageItems.add(expense)
                }
            }
        }

        // Assert the first page items
        Assert.assertEquals(nrEntries, firstPageItems.size)
        Assert.assertEquals("Expense 1", firstPageItems[0].description)
        Assert.assertEquals("Expense 10", firstPageItems[9].description)

        // Assert the second page items
        Assert.assertEquals(nrEntries, secondPageItems.size)
        Assert.assertEquals("Expense 51", secondPageItems[0].description)
        Assert.assertEquals("Expense 50", secondPageItems[9].description)


    }

}