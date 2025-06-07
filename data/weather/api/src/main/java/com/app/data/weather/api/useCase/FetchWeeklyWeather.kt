package com.app.data.weather.api.useCase

import com.app.data.weather.api.core.Resource
import com.app.data.weather.api.model.DailyForecast
import com.app.data.weather.api.model.ForecastModel

/**
 * UseCase для получения прогноза погоды на неделю по координатам.
 */
interface FetchWeeklyWeather {
    suspend operator fun invoke(lat: Double, lon: Double) : Resource<ForecastModel, String>
}