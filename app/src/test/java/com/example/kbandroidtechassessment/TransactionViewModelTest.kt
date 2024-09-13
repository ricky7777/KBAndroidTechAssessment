package com.example.kbandroidtechassessment

import com.example.kbandroidtechassessment.model.Transaction
import com.example.kbandroidtechassessment.repository.IRepository
import com.example.kbandroidtechassessment.repository.LocalRepository
import com.example.kbandroidtechassessment.viewmodel.TransactionViewModel
import com.example.kbandroidtechassessment.viewmodel.TransactionViewModelFactory
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class TransactionViewModelTest {

    private lateinit var viewModel: TransactionViewModel
    private val repository: IRepository = mock()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        // Set up mock repository
        val transactions = listOf(
            Transaction("1", "2024-09-01", 100.0),
            Transaction("2", "2024-09-15", 200.0),
            Transaction("3", "2024-09-20", 300.0)
        )
        whenever(repository.getTransaction()).thenReturn(transactions)
        val viewModelFactory = TransactionViewModelFactory(repository)
        viewModel = viewModelFactory.create(TransactionViewModel::class.java)
    }

    @Test
    fun `initial totalBalance is correct`() = testScope.runTest {
        // Ensure the dispatcher is used
        testDispatcher.scheduler.advanceUntilIdle()

        val expectedTotalBalance = 600.0
        assertEquals(expectedTotalBalance, viewModel.totalBalance.first())
    }

    @Test
    fun `resetTransaction restores all transactions and updates totalBalance`() =
        testScope.runTest {
            // Filter transactions test
            val dateRange = "2024-09-10" to "2024-09-20"
            viewModel.onDateRangeSelected(dateRange)

            // Ensure the dispatcher is used
            testDispatcher.scheduler.advanceUntilIdle()

            assertEquals(0.0, viewModel.totalBalance.first())

            viewModel.resetTransaction()

            testDispatcher.scheduler.advanceUntilIdle()

            val expectedTotalBalance = 600.0
            assertEquals(expectedTotalBalance, viewModel.totalBalance.first())
        }

    @Test
    fun `toggleShowDialog toggles the showDialog state`() = testScope.runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        assertFalse(viewModel.showDialog.first())

        viewModel.toggleShowDialog()
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.showDialog.first())

        viewModel.toggleShowDialog()
        testDispatcher.scheduler.advanceUntilIdle()
        assertFalse(viewModel.showDialog.first())
    }
}
