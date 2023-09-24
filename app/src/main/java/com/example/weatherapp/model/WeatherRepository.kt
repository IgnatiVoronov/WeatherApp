package com.example.weatherapp.model

import com.example.weatherapp.model.data.WeatherModelDTO
import retrofit2.Response

interface WeatherRepository {
    suspend fun getWeather(lat: String, lon: String): Response<WeatherModelDTO>
}