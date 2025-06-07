package com.app.weather.location

import android.location.Location
import com.app.weather.location.useCase.GetLocation
import com.app.weather.location.useCase.GetLocationImpl
import org.koin.dsl.module

val locationModule = module {
    single<Location> {
        Location("default").apply {
            latitude = 55.7558
            longitude = 37.6173
        } // Москва
    }

    single<LocationProvider> { LocationProviderImpl(get()) }
    single<GetLocation> { GetLocationImpl(get(), get()) }
}