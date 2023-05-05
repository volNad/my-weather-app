package com.example.myweatherapp.data.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

fun roundUpDoubleToString(number: Double): String {
    return ceil(number).toInt().toString()
}

fun formatTimeToHours(date: String): String {
    val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm")
    val outputFormat = DateTimeFormatter.ofPattern("HH:mm")

    val localDateTime = LocalDateTime.parse(date, inputFormat)

    return localDateTime.format(outputFormat)
}
