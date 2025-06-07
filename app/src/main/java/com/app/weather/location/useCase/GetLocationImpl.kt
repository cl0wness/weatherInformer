package com.app.weather.location.useCase

import android.location.Location
import com.app.weather.location.LocationProvider

class GetLocationImpl(
    private val locationProvider: LocationProvider,
    private val defaultLocation: Location
) : GetLocation {
    override suspend operator fun invoke(): Location {
        return try {
            locationProvider.getCurrentLocation() ?: defaultLocation
        } catch (e: Exception) {
            defaultLocation
        }
    }
}