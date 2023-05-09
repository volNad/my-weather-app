package com.example.myweatherapp.data.db.entity


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Condition(
    val text: String,
    val icon: String,
    val code: Int
): Serializable