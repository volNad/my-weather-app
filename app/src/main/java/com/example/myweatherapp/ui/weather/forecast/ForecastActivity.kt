package com.example.myweatherapp.ui.weather.forecast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.example.myweatherapp.data.network.ForecastApi
import com.example.myweatherapp.data.utils.formatDateToDay
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityForecastBinding
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.ceil

class ForecastActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForecastBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForecastBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pgForecast.visibility = View.VISIBLE

        Handler().postDelayed({
            // Hide progress bar
            binding.pgForecast.visibility = View.GONE
        }, 350)


        binding.ivBackBtn.setOnClickListener {
            onBackPressed()
        }


        val intent = getIntent()
        val cityName = intent.getStringExtra("city")
        binding.tvTomorrowLabel.text = "Tommorow"



        lifecycleScope.launch {

            val forecastApi = ForecastApi()
            val weather = forecastApi.getForecastWeatherData(cityName!!)
            val firstForecastDay = weather.forecast.forecastday[1]

           binding.tvTomorrowHumidityValue.text = "${firstForecastDay.day.avghumidity.toString()} %"
            val currentWind = firstForecastDay.day.maxwindKph
            val currentWindRoundUp = ceil(currentWind).toInt().toString()
            binding.tvTomorrowWindValue.text = "$currentWindRoundUp km/h"
            val rainFall = firstForecastDay.day.totalprecipMm
            val rainFallRoundUp = ceil(rainFall).toInt().toString()
            binding.tvTomorrowRainValue.text = "$rainFallRoundUp mm"
            val degrees = firstForecastDay.day.avgtempC
            val degreesRoundUp = ceil(degrees).toInt().toString()
            binding.tvTomorrowDegrees.text = "$degreesRoundUp °"
            val weatherCode = firstForecastDay.day.condition.code
            val weatherIcon = getWeatherIconDependOnResponse(weatherCode)
            binding.ivForecastTomorrow.setImageResource(weatherIcon)

            val secondForecastDay = weather.forecast.forecastday[2]
            val weatherCodeTwo = secondForecastDay.day.condition.code
            val weatherIconTwo = getWeatherIconDependOnResponse(weatherCodeTwo)
            binding.ivForestTwo.setImageResource(weatherIconTwo)
            val degreesTwo = secondForecastDay.day.avgtempC
            val degreesTwoRoundUp = ceil(degreesTwo).toInt().toString()
            binding.tvDegreesTwo.text = "$degreesTwoRoundUp °"

            val dayTwo = secondForecastDay.date
            binding.tvDayTwo.text = formatDateToDay(dayTwo)


            val thirdForecastDay = weather.forecast.forecastday[3]
            val weatherCodeThree = thirdForecastDay.day.condition.code
            val weatherIconThree = getWeatherIconDependOnResponse(weatherCodeThree)
            binding.ivForestThree.setImageResource(weatherIconThree)
            val degreesThree = thirdForecastDay.day.avgtempC
            val degreesThreeRoundUp = ceil(degreesThree).toInt().toString()
            binding.tvDegreesThree.text = "$degreesThreeRoundUp °"

            val dayThree = thirdForecastDay.date
            binding.tvDayThree.text = formatDateToDay(dayThree)

            val fourForecastDay = weather.forecast.forecastday[4]
            val weatherFourThree = fourForecastDay.day.condition.code
            val weatherIconFour = getWeatherIconDependOnResponse(weatherFourThree)
            binding.ivForestFour.setImageResource(weatherIconFour)
            val degreesFour = fourForecastDay.day.avgtempC
            val degreesFourRoundUp = ceil(degreesFour).toInt().toString()
            binding.tvDegreesFour.text = "$degreesFourRoundUp °"

            val dayFour = fourForecastDay.date
            binding.tvDayFour.text = formatDateToDay(dayFour)

            val fiveForecastDay = weather.forecast.forecastday[5]
            val weatherFive = fiveForecastDay.day.condition.code
            val weatherIconFive = getWeatherIconDependOnResponse(weatherFive)
            binding.ivForestFive.setImageResource(weatherIconFive)
            val degreesFive = fiveForecastDay.day.avgtempC
            val degreesFiveRoundUp = ceil(degreesFive).toInt().toString()
            binding.tvDegreesFive.text = "$degreesFiveRoundUp °"

            val dayFive = fiveForecastDay.date
            binding.tvDayFive.text = formatDateToDay(dayFive)

            val sixForecastDay = weather.forecast.forecastday[6]
            val weatherSix = sixForecastDay.day.condition.code
            val weatherIconSix = getWeatherIconDependOnResponse(weatherSix)
            binding.ivForestSix.setImageResource(weatherIconSix)
            val degreesSix = sixForecastDay.day.avgtempC
            val degreesSixRoundUp = ceil(degreesSix).toInt().toString()
            binding.tvDegreesSix.text = "$degreesSixRoundUp °"

            val daySix = sixForecastDay.date
            binding.tvDaySix.text = formatDateToDay(daySix)

            val sevenForecastDay = weather.forecast.forecastday[7]
            val weatherSeven = sevenForecastDay.day.condition.code
            val weatherIconSeven = getWeatherIconDependOnResponse(weatherSeven)
            binding.ivForestSeven.setImageResource(weatherIconSeven)
            val degreesSeven = sevenForecastDay.day.avgtempC
            val degreesSevenRoundUp = ceil(degreesSeven).toInt().toString()
            binding.tvDegreesSeven.text = "$degreesSevenRoundUp °"

            val daySeven = sevenForecastDay.date
            binding.tvDaySeven.text = formatDateToDay(daySeven)

        }



    }

    private fun getWeatherIconDependOnResponse(code: Int): Int {
        return when (code) {
            1000 -> R.drawable.sunny
            1003 -> R.drawable.sunny_cloudy
            1006 -> R.drawable.cloudy
            1009 -> R.drawable.cloudy
            1030 -> R.drawable.cloudy
            1063 -> R.drawable.cludy_rain
            1066 -> R.drawable.cloudy
            1069 -> R.drawable.cloudy
            1072 -> R.drawable.cludy_rain
            1087 -> R.drawable.cloudy
            1135 -> R.drawable.cloudy
            1147 -> R.drawable.cloudy
            1150 -> R.drawable.rain
            1153 -> R.drawable.rain
            1168 -> R.drawable.rain
            1171 -> R.drawable.rain
            1180 -> R.drawable.rain
            1183 -> R.drawable.rain
            1186 -> R.drawable.rain
            1189 -> R.drawable.rain
            1192 -> R.drawable.rain
            1195 -> R.drawable.rain
            1198 -> R.drawable.rain
            1201 -> R.drawable.rain
            1204 -> R.drawable.cloudy
            1207 -> R.drawable.cloudy
            1210 -> R.drawable.cloudy
            1213 -> R.drawable.cloudy
            1216 -> R.drawable.cloudy
            1219 -> R.drawable.cloudy
            1240 -> R.drawable.rain
            1243 -> R.drawable.rain
            1246 -> R.drawable.rain
            else -> R.drawable.sunny
        }
    }

}