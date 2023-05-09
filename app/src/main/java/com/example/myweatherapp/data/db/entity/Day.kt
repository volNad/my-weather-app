package com.example.myweatherapp.data.db.entity

import com.example.myweatherapp.data.db.entity.Condition
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Day(
    val avghumidity: Int,
    @SerializedName("avgtemp_c")
    val avgtempC: Double,
    val condition: Condition,
    @SerializedName("totalprecip_mm")
    val totalprecipMm: Double,
    @SerializedName("maxwind_kph")
    val maxwindKph: Double
): Serializable
