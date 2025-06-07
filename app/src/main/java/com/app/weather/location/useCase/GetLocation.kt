package com.app.weather.location.useCase

import android.location.Location

interface GetLocation {
    suspend operator fun invoke() : Location
}