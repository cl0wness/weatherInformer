package com.app.data.weather.api.core

/**
 * Обобщённый класс-обёртка для представления результата операции.
 *
 * Используется для инкапсуляции как успешных результатов (Success), так и ошибок (Error),
 * позволяя обрабатывать их безопасно и декларативно.
 *
 * @param D Тип данных при успешном результате
 * @param E Тип ошибки при неудачном результате
 */
sealed class Resource<out D, out E> {

    /**
     * Представляет успешный результат.
     *
     * @param data Полученные данные
     */
    data class Success<out D>(val data: D) : Resource<D, Nothing>()

    /**
     * Представляет ошибку.
     *
     * @param error Ошибочное состояние или сообщение
     */
    data class Error<out E>(val error: E) : Resource<Nothing, E>()

    /**
     * Возвращает true, если результат — успешный.
     */
    val isSuccess: Boolean get() = this is Success<D>

    /**
     * Возвращает true, если результат — ошибка.
     */
    val isError: Boolean get() = this is Error<E>

    /**
     * Преобразует данные в случае успеха, не затрагивая ошибку.
     *
     * @param transform Функция трансформации данных
     * @return Новый экземпляр Resource с преобразованными данными или текущая ошибка
     */
    fun <R> mapData(transform: (D) -> R): Resource<R, E> = when (this) {
        is Success -> Success(transform(data))
        is Error -> this
    }

    /**
     * Преобразует ошибку, не затрагивая данные.
     *
     * @param transform Функция трансформации ошибки
     * @return Новый экземпляр Resource с преобразованной ошибкой или текущий успех
     */
    fun <F> mapError(transform: (E) -> F): Resource<D, F> = when (this) {
        is Success -> this
        is Error -> Error(transform(error))
    }

    /**
     * Преобразует как данные, так и ошибку в зависимости от результата.
     *
     * @param dataTransform Функция трансформации данных
     * @param errorTransform Функция трансформации ошибки
     * @return Новый экземпляр Resource с преобразованными данными или ошибкой
     */
    fun <R, F> mapBoth(
        dataTransform: (D) -> R,
        errorTransform: (E) -> F
    ): Resource<R, F> = when (this) {
        is Success -> Success(dataTransform(data))
        is Error -> Error(errorTransform(error))
    }
}
