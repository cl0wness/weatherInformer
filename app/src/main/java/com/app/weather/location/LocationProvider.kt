package com.app.weather.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

interface LocationProvider {
    suspend fun getCurrentLocation(): Location?
}

class LocationProviderImpl(private val context: Context) : LocationProvider {

    private val locationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? = suspendCancellableCoroutine { cont ->

        val listener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                cont.resume(location)
                locationManager.removeUpdates(this)
            }

            override fun onProviderDisabled(provider: String) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }

        try {
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, listener, null)
        } catch (ex: Exception) {
            cont.resumeWithException(ex)
        }

        cont.invokeOnCancellation {
            locationManager.removeUpdates(listener)
        }
    }
}