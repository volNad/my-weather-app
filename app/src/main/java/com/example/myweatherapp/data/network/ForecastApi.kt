package com.example.myweatherapp.data.network

import com.example.myweatherapp.data.db.entity.Forecast
import com.example.myweatherapp.data.db.entity.Hour
import com.example.myweatherapp.data.network.response.CurrentWeatherResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface ForecastApi {
    @GET("forecast.json")
    suspend fun getForecastWeatherData(
        @Query("q") city: String,
        @Query("days") days: Int = 8
    ): CurrentWeatherResponse


    companion object {
        operator fun invoke(): ForecastApi {
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
                .create(ForecastApi::class.java)
        }
    }
}