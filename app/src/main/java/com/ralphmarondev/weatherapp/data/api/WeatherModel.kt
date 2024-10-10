package com.ralphmarondev.weatherapp.data.api

import com.ralphmarondev.weatherapp.data.util.Current
import com.ralphmarondev.weatherapp.data.util.Location

data class WeatherModel(
    val current: Current,
    val location: Location
)