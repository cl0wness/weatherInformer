package com.app.weather.screens.todayWeather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.data.weather.api.core.Resource
import com.app.data.weather.api.useCase.FetchCurrentWeather
import com.app.weather.location.useCase.GetLocation
import com.app.weather.screens.event.ScreenEvent
import com.app.weather.screens.event.UIEvent
import com.app.weather.screens.todayWeather.model.CurrentWeatherState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CurrentWeatherViewModel (
    private val fetchCurrentWeather: FetchCurrentWeather,
    private val getLocation: GetLocation
) : ViewModel() {
    // Состояние UI, доступно для подписки (immutable)
    private val _state = MutableStateFlow(CurrentWeatherState())
    val state = _state.asStateFlow()

    // Канал для одноразовых событий UI (например, ошибки)
    private val mUiEvents = Channel<UIEvent>()
    val uiEvents = mUiEvents.receiveAsFlow()

    /**
     * Обработка пользовательских событий экрана.
     * В частности, обновление погоды по запросу.
     */
    fun onEvent(event: ScreenEvent) {
        when(event) {
            is ScreenEvent.Refresh -> viewModelScope.launch {
                getWeather(true)
            }
        }
    }

    init {
        // Запускаем периодическую загрузку погоды сразу при создании ViewModel
        fetchWeatherPeriodically()
    }

    /**
     * Получение данных погоды с обновлением состояния и обработкой ошибок.
     * @param withLoading указывает, показывать ли индикатор загрузки
     */
    private suspend fun getWeather(withLoading: Boolean = false) {
        // Загрузка отображается только при первой загрузке (если это не рефреш), далее происходит фоном
        _state.value = _state.value.copy(isLoading = true && withLoading)

        val location = getLocation()
        val weatherResult = fetchCurrentWeather(location.latitude, location.longitude)

        when (weatherResult) {
            is Resource.Success -> {
                _state.value = _state.value.copy(weather = weatherResult.data)
            }
            is Resource.Error -> {
                mUiEvents.send(UIEvent.Error(weatherResult.error))
            }
        }
        _state.value = _state.value.copy(isLoading = false)
    }

    /**
     * Запуск бесконечного цикла для периодического обновления погоды.
     * Если данных погоды нет, показывает индикатор загрузки.
     */
    private fun fetchWeatherPeriodically() {
        viewModelScope.launch {
            while (true) {
                getWeather(state.value.weather == null)
                delay(AUTO_REFRESH_DELAY)
            }
        }
    }
}

const val AUTO_REFRESH_DELAY = 60_000L