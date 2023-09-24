package com.example.weatherapp

import com.example.weatherapp.domain.GetUiWeatherModelUseCase
import com.example.weatherapp.model.WeatherRepository
import com.example.weatherapp.model.data.Main
import com.example.weatherapp.model.data.WeatherModelDTO
import com.example.weatherapp.model.data.ui.WeatherInfo
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Response


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(MockitoJUnitRunner::class)
class ExampleUnitTest {
    private val weatherRepository: WeatherRepository = Mockito.mock(WeatherRepository::class.java)

    private val getUiWeatherModelUseCase: GetUiWeatherModelUseCase =
        GetUiWeatherModelUseCase(weatherRepository)

    @Test
    fun `farengeit to celsius from WeatherApi`() {
        var weatherInfo: WeatherInfo? = null
        runBlocking {
            whenever(weatherRepository.getWeather(anyString(), anyString())).thenReturn(
                Response.success(
                    WeatherModelDTO(
                        main = Main(
                            temp = 340.0,
                            humidity = 57
                        )
                    )
                )
            )



            weatherInfo = getUiWeatherModelUseCase.getUiWeatherModel("10.0", "10.0")
        }
        assertEquals(57, weatherInfo?.humidity)
        assertEquals(171.11, weatherInfo?.temperature ?: 0.0, 0.0)
    }
}