package com.app.data.weather.impl.useCase

import com.app.data.weather.api.core.Resource
import com.app.data.weather.api.model.DailyForecast
import com.app.data.weather.api.model.ForecastModel
import com.app.data.weather.api.repository.WeatherRepository
import com.app.data.weather.api.useCase.FetchWeeklyWeather

class FetchWeeklyWeatherImpl (
    private val repository: WeatherRepository
) : FetchWeeklyWeather{
    override suspend fun invoke(lat: Double, lon: Double): Resource<ForecastModel, String> {
        return repository.fetchWeeklyWeather(lat, lon)
    }

}