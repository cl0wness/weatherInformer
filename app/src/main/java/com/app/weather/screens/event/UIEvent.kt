package com.app.weather.screens.event

sealed class UIEvent {

    data class Error(val message: String) : UIEvent()
}