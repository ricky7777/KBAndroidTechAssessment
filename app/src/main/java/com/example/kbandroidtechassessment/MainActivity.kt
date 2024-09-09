package com.example.kbandroidtechassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.kbandroidtechassessment.ui.theme.KBAndroidTechAssessmentTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KBAndroidTechAssessmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        LargeTopAppBar(
                            title = {
                                Text("Travel Savings Account")
                            },
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
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Available Balance:  ",
                                fontWeight = FontWeight.W300,
                                fontSize = TextUnit(16f, TextUnitType.Sp),
                            )
                            Text(
                                text = "$0.00",
                                fontWeight = FontWeight.W600,
                                fontSize = TextUnit(24f, TextUnitType.Sp),
                            )
                        }
                        HorizontalDivider()
                        val transactions = getTransactions()
                        LazyColumn {
                            items(transactions) { transaction ->
                                TransactionItem(transaction = transaction)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: Transaction) {
    Row(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column(modifier = Modifier.weight(1f)) { // Left side
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

data class Transaction(
    val date: String,
    val description: String,
    val amount: Double
)

private fun getTransactions() = listOf(
    Transaction("2024-09-22", "Restaurant", -35.00),
    Transaction("2024-09-24", "Car Repair", -150.00),
    Transaction("2024-09-11", "Utilities", -150.00),
    Transaction("2024-09-19", "Clothing Store", -100.00),
    Transaction("2024-09-12", "Car Repair", -200.00),
    Transaction("2024-09-13", "Book Purchase", -30.00),
    Transaction("2024-09-14", "Electronics", -500.00),
    Transaction("2024-09-17", "Groceries", -70.00),
    Transaction("2024-09-26", "Electronics", -300.00),
    Transaction("2024-09-18", "Gym Membership", -50.00),
    Transaction("2024-09-01", "Coffee Shop", -15.00),
    Transaction("2024-09-02", "Grocery Store", -75.00),
    Transaction("2024-09-05", "Clothing Store", -120.00),
    Transaction("2024-09-06", "Gym Membership", -50.00),
    Transaction("2024-09-30", "Gym Membership", -50.00),
    Transaction("2024-09-15", "Vacation", -1500.00),
    Transaction("2024-09-07", "Movie Tickets", -30.00),
    Transaction("2024-09-08", "Salary", 2500.00),
    Transaction("2024-09-09", "Groceries", -80.00),
    Transaction("2024-09-23", "Groceries", -90.00),
    Transaction("2024-09-10", "Rent", -1200.00),
    Transaction("2024-09-20", "Movie Tickets", -25.00),
    Transaction("2024-09-21", "Gas Station", -55.00),
    Transaction("2024-09-25", "Utilities", -120.00),
    Transaction("2024-09-27", "Vacation", -1000.00),
    Transaction("2024-09-28", "Restaurant", -45.00),
    Transaction("2024-09-29", "Groceries", -85.00),
    Transaction("2024-09-16", "Restaurant", -40.00),
    Transaction("2024-09-03", "Restaurant", -35.00),
    Transaction("2024-09-04", "Gas Station", -60.00),
)