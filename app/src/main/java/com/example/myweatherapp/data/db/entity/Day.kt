package com.example.myweatherapp.data.db.entity

import com.example.myweatherapp.data.db.entity.Condition
import com.google.gson.annotations.SerializedName

data class Day(
    val avghumidity: Int,
    @SerializedName("avgtemp_c")
    val avgtempC: Double,
    val condition: Condition,
    @SerializedName("totalprecip_mm")
    val totalprecipMm: Double,
)
