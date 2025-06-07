package com.app.data.weather.api.useCase

import com.app.data.weather.api.core.Resource
import com.app.data.weather.api.model.CurrentWeatherModel
import com.app.data.weather.api.model.WeatherModel

/**
 * UseCase для получения текущей погоды по координатам.
 */
interface FetchCurrentWeather {
    suspend operator fun invoke(lat: Double, lon: Double) : Resource<CurrentWeatherModel, String>
}