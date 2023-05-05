package com.example.myweatherapp.data.network.response


import com.example.myweatherapp.data.db.entity.CurrentWeatherEntry
import com.example.myweatherapp.data.db.entity.Forecast
import com.example.myweatherapp.data.db.entity.Location
import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    val forecast: Forecast,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,


)