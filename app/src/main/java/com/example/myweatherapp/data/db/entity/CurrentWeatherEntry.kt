package com.example.myweatherapp.data.db.entity


import com.google.gson.annotations.SerializedName




data class CurrentWeatherEntry(
    val condition: Condition,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("precip_in")
    val precipIn: Double,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("temp_f")
    val tempF: Double,
    @SerializedName("wind_kph")
    val windKph: Double,
    @SerializedName("wind_mph")
    val windMph: Double
)