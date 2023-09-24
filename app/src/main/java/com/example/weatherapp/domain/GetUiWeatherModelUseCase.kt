package com.example.weatherapp.domain

import com.example.weatherapp.model.WeatherRepository
import com.example.weatherapp.model.data.ui.WeatherInfo
import javax.inject.Inject

class GetUiWeatherModelUseCase(
    private val repo: WeatherRepository
) {
    suspend fun getUiWeatherModel(lat: String, lon: String): WeatherInfo? {
        val result = repo.getWeather(lat, lon)
        return if (result.isSuccessful) {
            result.body()?.let { dto ->
                WeatherInfo(
                    temperature = farengheitToCelsius(dto.main?.temp),
                    humidity = dto.main?.humidity ?: 0
                )
            }
        } else {
            null
        }
    }

    private fun farengheitToCelsius(farengheit: Double?): Double { //TODO: I did here mistake, we receive Kelvins from BE
        return if (farengheit != null)
            (farengheit - 32) / 1.8
        else {
            0.0
        }
    }
}