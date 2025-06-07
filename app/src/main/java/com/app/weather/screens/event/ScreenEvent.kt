package com.app.weather.screens.event

sealed class ScreenEvent {

    data object Refresh : ScreenEvent()
}