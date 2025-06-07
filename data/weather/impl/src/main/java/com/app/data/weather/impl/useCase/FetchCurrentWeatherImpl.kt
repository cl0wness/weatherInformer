package com.app.data.weather.impl.useCase

import com.app.data.weather.api.core.Resource
import com.app.data.weather.api.model.CurrentWeatherModel
import com.app.data.weather.api.repository.WeatherRepository
import com.app.data.weather.api.useCase.FetchCurrentWeather

class FetchCurrentWeatherImpl (
    private val repository: WeatherRepository
) : FetchCurrentWeather{
    override suspend fun invoke(lat: Double, lon: Double): Resource<CurrentWeatherModel, String> {
        return repository.fetchCurrentWeather(lat, lon)
    }

}