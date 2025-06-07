package com.app.weather.screens.di

import com.app.weather.screens.todayWeather.CurrentWeatherViewModel
import com.app.weather.screens.weekWeather.WeeklyWeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { WeeklyWeatherViewModel(get(), get()) }
    viewModel { CurrentWeatherViewModel(get(), get()) }
}