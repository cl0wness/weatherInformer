package com.app.data.weather.impl.di

import com.app.data.weather.api.repository.WeatherRepository
import com.app.data.weather.api.useCase.FetchCurrentWeather
import com.app.data.weather.api.useCase.FetchWeeklyWeather
import com.app.data.weather.impl.remote.api.WeatherApi
import com.app.data.weather.impl.remote.dataSource.WeatherDataSource
import com.app.data.weather.impl.repository.WeatherRepositoryImpl
import com.app.data.weather.impl.useCase.FetchCurrentWeatherImpl
import com.app.data.weather.impl.useCase.FetchWeeklyWeatherImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create


val weatherApiModule = module {
    single<WeatherApi> { get<Retrofit>().create() }
    single { WeatherDataSource(get()) }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
    single<FetchCurrentWeather> { FetchCurrentWeatherImpl(get()) }
    single<FetchWeeklyWeather> { FetchWeeklyWeatherImpl(get()) }
}

