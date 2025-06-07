package com.app.data.weather.impl.remote.dataSource.core

import com.app.data.weather.api.core.Resource
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.io.IOException

/**
 * Универсальная функция для выполнения сетевого запроса и обработки результата.
 *
 * @param call лямбда, выполняющая сетевой вызов и возвращающая Response<T>.
 * @return Resource.Success с данными при успешном ответе или Resource.Error с сообщением ошибки.
 *
 * Обработка ошибок:
 * - Возвращает ошибку с сообщением при сетевых ошибках (IOException).
 * - Обрабатывает ошибки сериализации и случаи, когда тело ответа отсутствует (NullPointerException).
 * - Для остальных исключений пробрасывает дальше.
 *
 * @throws Exception пробрасывает непредвиденные исключения, не относящиеся к сериализации или null.
 */
suspend inline fun <reified T> getResult(
    call: () -> Response<T>
): Resource<T, String> {
    try {
        val response = call()
        return if (response.isSuccessful) {
            val body: T = response.body() ?: throw NullPointerException("Response body is null")
            Resource.Success(body)
        } else {
            val errorBody = response.errorBody()?.string().orEmpty().ifBlank { "Unknown error" }
            Resource.Error(errorBody)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        return Resource.Error(e.message ?: "Network error")
    } catch (e: Exception) {
        e.printStackTrace()
        return when (e) {
            is SerializationException, is NullPointerException -> Resource.Error(
                e.message ?: "Serialization or null pointer error"
            )
            else -> throw e
        }
    }
}