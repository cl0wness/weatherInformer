package com.app.weather.screens.todayWeather.model

import com.app.data.weather.api.model.CurrentWeatherModel

/**
 * Состояние экрана текущей погоды.
 *
 * @param isLoading Флаг загрузки данных
 * @param weather Данные текущей погоды или null, если отсутствуют
 */
data class CurrentWeatherState(
    val isLoading: Boolean = false,
    val weather: CurrentWeatherModel? = null,
)
