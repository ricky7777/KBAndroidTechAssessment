package com.example.kbandroidtechassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getString
import com.example.kbandroidtechassessment.model.Transaction
import com.example.kbandroidtechassessment.repository.LocalRepository
import com.example.kbandroidtechassessment.ui.theme.KBAndroidTechAssessmentTheme

/**
 * @Author Ricky
 * this is main entry point, transaction detail ui use compose
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KBAndroidTechAssessmentTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = { TopAppBar() }
                ) { innerPadding ->
                    TransactionDetailContent(innerPadding)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar() {
    LargeTopAppBar(
        title = { Text("Travel Savings Account") },
        actions = {
            IconButton(onClick = { /* Handle filter action */ }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Filter"
                )
            }
        }
    )
}

@Composable
fun TransactionDetailContent(innerPadding: PaddingValues) {
    Column(modifier = Modifier.padding(innerPadding)) {
        BalanceInfo()
        HorizontalDivider()
        TransactionList()
    }
}

@Composable
fun BalanceInfo() {
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
            text = getString(context, R.string.balance_default_value),
            fontWeight = FontWeight.W600,
            fontSize = with(density) { contextFontSize.toSp() },
        )
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
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
fun TransactionList() {
    val transactions = LocalRepository().getTransaction()
    LazyColumn {
        items(transactions) { transaction ->
            TransactionItem(transaction = transaction)
        }
    }
}