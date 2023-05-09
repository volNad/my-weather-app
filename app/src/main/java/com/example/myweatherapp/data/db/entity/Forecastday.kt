package com.example.myweatherapp.data.db.entity


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Forecastday(
    val date: String,
    @SerializedName("date_epoch")
    val dateEpoch: Int,
    val day: Day,
    val hour: List<Hour>
): Serializable
