package com.example.myweatherapp.ui

import android.Manifest
import kotlin.math.ceil
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.location.LocationRequest
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.myweatherapp.data.network.WeatherApi
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.*


class MainActivity : AppCompatActivity(){

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var request: LocationRequest
    private lateinit var binding: ActivityMainBinding
    private val weatherApi = WeatherApi()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        binding.ivSearch.setOnClickListener {
            binding.ivMore.visibility = View.GONE
            binding.ivPoint.visibility = View.GONE
            binding.etSearchCity.visibility = View.VISIBLE
            binding.btnSearchCity.visibility = View.VISIBLE
        }



        if (!isLocationEnabled()) {
            Toast.makeText(this, "Turn on you GPS", Toast.LENGTH_LONG).show()
            val intentSettings = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intentSettings)
        } else {
            Dexter.withActivity(this)
                .withPermissions(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .withListener(object: MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report!!.areAllPermissionsGranted()) {
                            requestLocationData()
                        }

                        if(report.isAnyPermissionPermanentlyDenied) {
                            Toast.makeText(
                                this@MainActivity,
                                "You have denied location permissions",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogForPermissions()
                    }

                }).onSameThread().check()
        }

    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("You have turned off permissions. Please turn it on")
            .setPositiveButton("Go TO SETTINGS") {_,_ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL") {dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun isLocationEnabled(): Boolean{
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }



  @SuppressLint("MissingPermission")
  private fun  requestLocationData() {
      mFusedLocationClient.lastLocation.addOnSuccessListener {location ->
          val longitude = location.longitude
          val latitude = location.latitude
          if(latitude != null && longitude != null) {
              CoroutineScope(Dispatchers.Main).launch {

              try {
                  binding.btnSearchCity.setOnClickListener {
                      val textCity = binding.etSearchCity.text
                      binding.ivMore.visibility = View.VISIBLE
                      binding.ivPoint.visibility = View.VISIBLE
                      binding.etSearchCity.visibility = View.GONE
                      binding.btnSearchCity.visibility = View.GONE


                      CoroutineScope(Dispatchers.Main).launch{
                          val currentWeather = weatherApi.getWeatherData("$textCity")
                          val currentHumidity = currentWeather.currentWeatherEntry.humidity.toString()
                          val currentWind = currentWeather.currentWeatherEntry.windKph
                          val currentWindRoundUp = ceil(currentWind).toInt().toString()
                          val rainFall = currentWeather.currentWeatherEntry.precipMm
                          val rainfallRoundUp = ceil(rainFall).toInt().toString()
                          val placeName = currentWeather.location.name
                          val countryName = currentWeather.location.country
                          val degrees = currentWeather.currentWeatherEntry.tempC
                          val date = currentWeather.location.localtime
                          val dayOfWeek = dateToDayFormatter(date)
                          val monthAndDay = dateToMonthAndDay(date)
                          val degreesRoundUp = ceil(degrees).toInt().toString()
                          val condition = currentWeather.currentWeatherEntry.condition.text
                          val weatherCode = currentWeather.currentWeatherEntry.condition.code
                          val weatherIcon = getWeatherIconDependOnResponse(weatherCode)
                          binding?.tvHumidityValue?.text = "$currentHumidity%"
                          binding?.tvWindValue?.text = "$currentWindRoundUp km/h"
                          binding?.tvRainFallValue?.text = "$rainfallRoundUp mm"
                          binding?.tvPlace?.text = "$countryName,\n$placeName"
                          binding?.tvDegrees?.text = degreesRoundUp
                          binding?.tvTime?.text = "$dayOfWeek, $monthAndDay"
                          binding?.tvWeatherType?.text = condition
                          binding?.ivWeather?.setImageResource(weatherIcon)
                      }


                  }
              }  catch (e: HttpException) {
                  if (e.code() == 400) {
                        Log.e("Wrong City", "City wrong")
                  } else {
                      showAlertDialog()
                  }
              }


                  val currentWeather = weatherApi.getWeatherData("$latitude,$longitude")
                  val currentHumidity = currentWeather.currentWeatherEntry.humidity.toString()
                  val currentWind = currentWeather.currentWeatherEntry.windKph
                  val currentWindRoundUp = ceil(currentWind).toInt().toString()
                  val rainFall = currentWeather.currentWeatherEntry.precipMm
                  val rainfallRoundUp = ceil(rainFall).toInt().toString()
                  val placeName = currentWeather.location.name
                  val countryName = currentWeather.location.country
                  val degrees = currentWeather.currentWeatherEntry.tempC
                  val date = currentWeather.location.localtime
                  val dayOfWeek = dateToDayFormatter(date)
                  val monthAndDay = dateToMonthAndDay(date)
                  val degreesRoundUp = ceil(degrees).toInt().toString()
                  val condition = currentWeather.currentWeatherEntry.condition.text
                  val weatherCode = currentWeather.currentWeatherEntry.condition.code
                  val weatherIcon = getWeatherIconDependOnResponse(weatherCode)
                  binding?.tvHumidityValue?.text = "$currentHumidity%"
                  binding?.tvWindValue?.text = "$currentWindRoundUp km/h"
                  binding?.tvRainFallValue?.text = "$rainfallRoundUp mm"
                  binding?.tvPlace?.text = "$countryName,\n$placeName"
                  binding?.tvDegrees?.text = degreesRoundUp
                  binding?.tvTime?.text = "$dayOfWeek, $monthAndDay"
                  binding?.tvWeatherType?.text = condition
                  binding?.ivWeather?.setImageResource(weatherIcon)



              }
      }
      }
  }

private fun dateToDayFormatter(date: String): String{
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm")
    val date = LocalDate.parse(date, formatter)
    val dayOfWeek = date.dayOfWeek
    val dayOfWeekAbbr = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    return dayOfWeekAbbr
}

    private fun dateToMonthAndDay(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd H:mm")
        val date = LocalDate.parse(date, formatter)

        val monthAbbreviated = date.month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        val dayOfMonth = date.dayOfMonth

        val formattedDate = "$monthAbbreviated $dayOfMonth"
        return formattedDate
    }

    private fun getWeatherIconDependOnResponse(code: Int): Int {
        return when (code) {
            1000 -> R.drawable.sunny
            1003 -> R.drawable.cloudy
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

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setMessage("Oops! You`ve enter a wrong name. Try again!")
            .setPositiveButton("OK") {dialog,_ ->
                try {
                    dialog.dismiss()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL") {dialog, _ ->
                dialog.dismiss()
            }.show()
    }


    fun showMessage(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    }




