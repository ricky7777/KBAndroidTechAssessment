package com.example.kbandroidtechassessment

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.runtime.mutableStateOf
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
import androidx.lifecycle.ViewModelProvider
import com.example.kbandroidtechassessment.model.Transaction
import com.example.kbandroidtechassessment.ui.theme.KBAndroidTechAssessmentTheme
import com.example.kbandroidtechassessment.viewmodel.TransactionViewModel

/**
 * @Author Ricky
 * this is main entry point, transaction detail ui use compose
 */
class MainActivity : AppCompatActivity() {
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)
        val supportFragmentManager = getSupportFragmentManager(this)
        setContent {
            KBAndroidTechAssessmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            supportFragmentManager,
                            transactionViewModel
                        )
                    }) { innerPadding ->
                    TransactionDetailContent(innerPadding, transactionViewModel)
                }
            }
        }
    }

    private fun getSupportFragmentManager(context: Context): FragmentManager? {
        return (context as? AppCompatActivity)?.supportFragmentManager
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    supportFragmentManager: FragmentManager?,
    transactionViewModel: TransactionViewModel
) {
    val context = LocalContext.current

    val showDialog = remember { mutableStateOf(false) }

    if (showDialog.value) {
        val pickerDialog = remember {
            TransactionPickerDialog(context, supportFragmentManager) { dateRange ->
                transactionViewModel.filterTransactions(dateRange)
            }
        }

        LaunchedEffect(showDialog) {
            pickerDialog.show()
            showDialog.value = false
        }
    }

    LargeTopAppBar(title = {
        Text(
            getString(
                context,
                R.string.transaction_detail_title
            )
        )
    }, actions = {
        Row {
            IconButton(onClick = {
                transactionViewModel.resetTransaction()
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Reset"
                )
            }

            // 原本的日期範圍圖標
            IconButton(onClick = {
                showDialog.value = true
            }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Filter"
                )
            }
        }
    })
}

@Composable
private fun TransactionDetailContent(
    innerPadding: PaddingValues,
    transactionViewModel: TransactionViewModel
) {
    val transactions by transactionViewModel.filteredTransactions.collectAsState()

    val totalBalance = remember { mutableStateOf(0.00) }
    LaunchedEffect(transactions) {
        totalBalance.value = transactionViewModel.filteredTransactions.value.sumOf { it.amount }
    }

    Column(modifier = Modifier.padding(innerPadding)) {
        BalanceInfo(totalBalance.value)
        HorizontalDivider()
        TransactionList(transactionViewModel.filteredTransactions.value)
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