package com.app.weather.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val originalRequest = chain.request()
                val url = originalRequest.url.newBuilder()
                    .addQueryParameter("key", "09995898a16f474e9f5200254250706")
                    .build()
                val request = originalRequest.newBuilder().url(url).build()
                chain.proceed(request)
            }
            .build()
    }

    single {
        val contentType = "application/json".toMediaType()
        val json = Json { ignoreUnknownKeys = true }

        Retrofit.Builder()
            .baseUrl("https://api.weatherbit.io/v2.0/") // новая база
            .client(get())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}