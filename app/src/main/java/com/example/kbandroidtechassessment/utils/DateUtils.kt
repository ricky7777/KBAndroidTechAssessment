package com.example.kbandroidtechassessment.utils

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

/**
 * @author Ricky
 * date reference utils
 */
object DateUtils {

    val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    fun formatDate(timeInMillis: Long?): String {
        return if (timeInMillis != null) {
            val date = Date(timeInMillis)
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            format.format(date)
        } else {
            "N/A"
        }
    }

    /**
     * Get the start date and end date from today by going back n days.
     *
     * @param days The number of days to go back.
     * @return A Pair containing startDate and endDate as strings.
     */
    fun getDateRangeFromToday(days: Int): Pair<String, String> {
        val today = LocalDate.now()
        val startDate = today.minusDays(days.toLong())
        val endDate = today
        return Pair(startDate.format(DATE_FORMATTER), endDate.format(DATE_FORMATTER))
    }
}