package com.example.kbandroidtechassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.kbandroidtechassessment.model.Transaction
import com.example.kbandroidtechassessment.repository.IRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * keep transactions when filtered
 * control balance and show dialog logic
 */
class TransactionViewModel(private val repository: IRepository) : ViewModel() {
    private val transactionsSource = repository.getTransaction()
    private val _filteredTransactions = MutableStateFlow(transactionsSource)
    val filteredTransactions: StateFlow<List<Transaction>> = _filteredTransactions

    private val _totalBalance = MutableStateFlow(0.00)
    val totalBalance: StateFlow<Double> = _totalBalance

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    init {
        calculateTotalBalance(transactionsSource)
    }

    private fun filterTransactions(dateRange: Pair<String, String>) {
        viewModelScope.launch {
            val filteredList = transactionsSource.filter { transaction ->
                transaction.date >= dateRange.first && transaction.date <= dateRange.second
            }
            _filteredTransactions.value = filteredList
            calculateTotalBalance(filteredList)
        }
    }

    fun resetTransaction() {
        _filteredTransactions.value = repository.getTransaction()
        _filteredTransactions.value = transactionsSource
        calculateTotalBalance(transactionsSource)
    }

    private fun calculateTotalBalance(transactions: List<Transaction>) {
        _totalBalance.value = transactions.sumOf { it.amount }
    }

    fun toggleShowDialog() {
        _showDialog.value = !_showDialog.value
    }

    fun onDateRangeSelected(dateRange: Pair<String, String>) {
        filterTransactions(dateRange)
        _showDialog.value = false
    }

}


class TransactionViewModelFactory(private val repository: IRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}