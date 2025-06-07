package com.app.weather.screens.weekWeather.model

import com.app.data.weather.api.model.CurrentWeatherModel
import com.app.data.weather.api.model.DailyForecast
import com.app.data.weather.api.model.ForecastModel

/**
 * Состояние экрана погоды на неделю.
 *
 * @param isLoading Флаг загрузки данных
 * @param weekWeather Данные текущей погоды или null, если отсутствуют
 */
data class WeeklyWeatherState(
    val isLoading: Boolean = false,
    val weekWeather: ForecastModel? = null,
)