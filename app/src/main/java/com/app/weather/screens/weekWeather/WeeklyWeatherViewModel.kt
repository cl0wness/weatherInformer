package com.app.weather.screens.weekWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.weather.api.core.Resource
import com.app.data.weather.api.useCase.FetchWeeklyWeather
import com.app.weather.location.useCase.GetLocation
import com.app.weather.screens.event.UIEvent
import com.app.weather.screens.todayWeather.AUTO_REFRESH_DELAY
import com.app.weather.screens.weekWeather.model.WeeklyWeatherState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class WeeklyWeatherViewModel (
    private val fetchWeeklyWeather: FetchWeeklyWeather,
    private val getLocation: GetLocation
) : ViewModel() {
    // Состояние UI для подписки
    private val _state = MutableStateFlow(WeeklyWeatherState())
    val state = _state.asStateFlow()

    // Канал для событий UI (например, ошибок)
    private val mUiEvents = Channel<UIEvent>()
    val uiEvents = mUiEvents.receiveAsFlow()

    init {
        // Запуск периодической загрузки прогноза при старте ViewModel
        fetchWeatherPeriodically()
    }

    /**
     * Периодическое обновление недельного прогноза погоды.
     */
    private fun fetchWeatherPeriodically() {
        viewModelScope.launch {
            while (true) {
                if (state.value.weekWeather == null) {
                    _state.value = _state.value.copy(isLoading = true)
                }

                val location = getLocation()
                val weatherResult = fetchWeeklyWeather(location.latitude, location.longitude)

                when (weatherResult) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(weekWeather = weatherResult.data)
                    }
                    is Resource.Error -> {
                        mUiEvents.send(UIEvent.Error(weatherResult.error))
                    }
                }

                _state.value = _state.value.copy(isLoading = false)

                delay(AUTO_REFRESH_DELAY)
            }
        }
    }
}