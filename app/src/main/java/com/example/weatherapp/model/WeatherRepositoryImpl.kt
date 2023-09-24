package com.example.weatherapp.model

import com.example.weatherapp.model.data.WeatherModelDTO
import com.example.weatherapp.model.data.ui.WeatherInfo
import com.example.weatherapp.model.network.WeatherApiInterface
import retrofit2.Response

class WeatherRepositoryImpl(val weatherApi: WeatherApiInterface) : WeatherRepository {
    override suspend fun getWeather(lat: String, lon: String): Response<WeatherModelDTO> =
        weatherApi.getWeatherByLocation(lat = lat, lon = lon)

}