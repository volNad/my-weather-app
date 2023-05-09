package com.example.myweatherapp.data.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*
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

fun formatDateToDay(date: String): String {
        val date = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)
    return date.dayOfWeek.getDisplayName(
        TextStyle.FULL,
        Locale.ENGLISH
    )
}
