package com.example.kbandroidtechassessment.model

/**
 * Transaction data class, every transaction receive from data repository
 */
data class Transaction(
    val date: String,
    val description: String,
    val amount: Double
)
