package com.example.kbandroidtechassessment.repository

import com.example.kbandroidtechassessment.model.Transaction

/**
 * Repository pattern
 * use this pattern to expand data center
 * and if data receive change style, don't need change method
 * use this interface to restrict front.
 */
interface IRepository {
    fun getTransaction(): List<Transaction>
}