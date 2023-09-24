package com.example.weatherapp.model.network

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.data.WeatherModelDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {

    @GET("/data/2.5/weather")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String = BuildConfig.API_KEY
    ): Response<WeatherModelDTO>
}