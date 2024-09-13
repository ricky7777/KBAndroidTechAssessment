package com.example.kbandroidtechassessment.compose

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import androidx.fragment.app.FragmentManager
import com.example.kbandroidtechassessment.R
import com.example.kbandroidtechassessment.dialog.DatePickerDialog
import com.example.kbandroidtechassessment.model.Transaction
import com.example.kbandroidtechassessment.viewmodel.TransactionViewModel

/**
 * @author Ricky
 * all Composable put here
 */
@Composable
fun TxDetailScreen(fragmentManager: FragmentManager, transactionViewModel: TransactionViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(fragmentManager, transactionViewModel)
        }) { innerPadding ->
        TransactionDetailContent(innerPadding, transactionViewModel)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    supportFragmentManager: FragmentManager?,
    transactionViewModel: TransactionViewModel
) {
    val context = LocalContext.current

    TransactionPickerDialog(context, supportFragmentManager, transactionViewModel)

    val appBarTitle = getString(context, R.string.transaction_detail_title)
    LargeTopAppBar(title = { Text(appBarTitle) }, actions = {
        Row {
            ResetButton(transactionViewModel)
            FilterButton(transactionViewModel)
        }
    })
}

@Composable
private fun TransactionPickerDialog(
    context: Context,
    supportFragmentManager: FragmentManager?,
    transactionViewModel: TransactionViewModel
) {
    val showDialog by transactionViewModel.showDialog.collectAsState()

    if (showDialog) {
        val pickerDialog = remember {
            DatePickerDialog(context, supportFragmentManager) { dateRange ->
                transactionViewModel.onDateRangeSelected(dateRange)
            }
        }

        LaunchedEffect(showDialog) {
            pickerDialog.show()
        }
    }
}

@Composable
private fun FilterButton(transactionViewModel: TransactionViewModel) {
    IconButton(onClick = {
        transactionViewModel.toggleShowDialog()
    }) {
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = "Filter"
        )
    }
}

@Composable
private fun ResetButton(transactionViewModel: TransactionViewModel) {
    IconButton(onClick = {
        transactionViewModel.resetTransaction()
    }) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Reset"
        )
    }
}

@Composable
private fun TransactionDetailContent(
    innerPadding: PaddingValues,
    transactionViewModel: TransactionViewModel
) {
    val transactions by transactionViewModel.filteredTransactions.collectAsState()
    val totalBalance by transactionViewModel.totalBalance.collectAsState()

    Column(modifier = Modifier.padding(innerPadding)) {
        BalanceInfo(totalBalance)
        HorizontalDivider()
        TransactionList(transactions)
    }
}

@Composable
private fun BalanceInfo(totalBalance: Double) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val titleFontSize = dimensionResource(R.dimen.font_size_title_16)
    val contextFontSize = dimensionResource(R.dimen.font_size_context_24)

    Row(modifier = Modifier.padding(16.dp)) {
        Text(
            text = getString(context, R.string.balance_title),
            fontWeight = FontWeight.W300,
            fontSize = with(density) { titleFontSize.toSp() },
        )
        Text(
            text = String.format(getString(context, R.string.balance_value), totalBalance),
            fontWeight = FontWeight.W600,
            fontSize = with(density) { contextFontSize.toSp() },
        )
    }
}

@Composable
private fun TransactionItem(transaction: Transaction) {
    val horizontal = dimensionResource(R.dimen.ui_transaction_item_horizontal)
    val vertical = dimensionResource(R.dimen.ui_transaction_item_vertical)
    Row(modifier = Modifier.padding(horizontal, vertical = vertical)) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = transaction.description)
            Text(text = transaction.date, style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "$${transaction.amount}",
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
                .align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun TransactionList(transactions: List<Transaction>) {
    LazyColumn {
        items(transactions) { transaction ->
            TransactionItem(transaction = transaction)
        }
    }
}