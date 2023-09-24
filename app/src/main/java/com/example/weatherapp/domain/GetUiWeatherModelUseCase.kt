package com.example.weatherapp.domain

import com.example.weatherapp.model.WeatherRepository
import com.example.weatherapp.model.data.ui.WeatherInfo

class GetUiWeatherModelUseCase(
    private val repo: WeatherRepository
) {
    suspend fun getUiWeatherModel(lat: String, lon: String): WeatherInfo? {
        val result = repo.getWeather(lat, lon)
        return if (result.isSuccessful) {
            result.body()?.let { dto ->
                WeatherInfo(
                    temperature = kelvinToCelsius(dto.main?.temp),
                    humidity = dto.main?.humidity ?: 0
                )
            }
        } else {
            null
        }
    }

    private fun kelvinToCelsius(kelvin: Double?): Double {
        return if (kelvin != null)
            kelvin - 273.15
        else {
            0.0
        }
    }
}