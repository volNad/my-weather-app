package com.example.myweatherapp.data.db.entity

import com.example.myweatherapp.data.db.entity.Condition
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Hour(
    val condition: Condition,
    val humidity: Int,
    @SerializedName("precip_mm")
    val precipMm: Double,
    @SerializedName("temp_c")
    val tempC: Double,
    val time: String,
    @SerializedName("wind_kph")
    val windKph: Double
): Serializable
