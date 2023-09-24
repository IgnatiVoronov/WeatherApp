package com.example.weatherapp.model

import com.example.weatherapp.model.data.Main
import com.example.weatherapp.model.data.WeatherModelDTO
import kotlinx.coroutines.delay
import retrofit2.Response

class TestingWeatherRepositoryImpl() : WeatherRepository {
    override suspend fun getWeather(lat: String, lon: String): Response<WeatherModelDTO> {
        delay(2_000)
        return Response.success(
            WeatherModelDTO(
                main = Main(
                    temp = 340.0,
                    humidity = 57
                )
            )
        )
    }
}