package com.example.myweatherapp.data.network

import com.example.myweatherapp.data.network.response.CurrentWeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "0a5d78aa5e2141df8fe191622233004"

interface WeatherApi {
    @GET("current.json")
    suspend fun getWeatherData(
        @Query("q") city: String
    ): CurrentWeatherResponse

    companion object {
        operator fun invoke(): WeatherApi {
            val requestInterceptor = Interceptor {chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)
            }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }
}

