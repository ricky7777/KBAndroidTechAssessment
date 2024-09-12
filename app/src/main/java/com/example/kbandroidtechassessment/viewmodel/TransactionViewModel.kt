package com.example.kbandroidtechassessment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kbandroidtechassessment.model.Transaction
import com.example.kbandroidtechassessment.repository.LocalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * keep transactions when filtered
 */
class TransactionViewModel : ViewModel() {
    private val transactionsSource = LocalRepository().getTransaction()
    private val _filteredTransactions = MutableStateFlow(transactionsSource)
    val filteredTransactions: StateFlow<List<Transaction>> = _filteredTransactions

    fun filterTransactions(dateRange: Pair<String, String>) {
        viewModelScope.launch {
            val filteredList = transactionsSource.filter { transaction ->
                transaction.date >= dateRange.first && transaction.date <= dateRange.second
            }
            _filteredTransactions.value = filteredList
        }
    }

    fun resetTransaction(){
        _filteredTransactions.value = LocalRepository().getTransaction()
    }

}