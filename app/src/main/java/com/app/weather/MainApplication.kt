package com.app.weather

import android.app.Application
import com.app.data.weather.impl.di.weatherApiModule
import com.app.weather.di.appModule
import com.app.weather.location.locationModule
import com.app.weather.screens.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(appModule)
            modules(locationModule)
            modules(weatherApiModule)
            modules(viewModelsModule)
        }
    }
}