package com.example.weatherapp.utils

import com.example.weatherapp.model.data_cities.City
import com.example.weatherapp.model.data_cities.Coordinates

//this is not used in the project yet
object Cities {
    val cities = mapOf(
        City("Minsk") to Coordinates(53.9, 27.5),
        City("Warsaw") to Coordinates(52.2, 21.0),
        City("Sydney") to Coordinates(-33.86, 151.21),
        City("Reykjavík") to Coordinates(64.14, -21.94)
    )
}