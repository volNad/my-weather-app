package com.example.myweatherapp.data.db.entity

import java.io.Serializable


data class Forecast(
    val forecastday: List<Forecastday>
): Serializable
