package com.app.weather.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit

val appModule = module {
    // OkHttp с добавлением ключа API
    single {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val originalUrl = original.url

                val url = originalUrl.newBuilder()
                    .addQueryParameter("key", "7125fcecd897483cb4f214840250706")
                    .build()

                val request = original.newBuilder()
                    .url(url)
                    .build()

                chain.proceed(request)
            }
            .build()
    }

    // Json конвертер
    single {
        Json { ignoreUnknownKeys = true }
    }

    // Retrofit-инстанс
    single {
        val contentType = "application/json".toMediaType()
        val json: Json = get()

        Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .client(get())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }
}