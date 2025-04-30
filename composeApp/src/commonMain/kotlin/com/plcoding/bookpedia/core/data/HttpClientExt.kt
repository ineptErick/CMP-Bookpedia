package com.plcoding.bookpedia.core.data

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

// используется для обработки HTTP-запросов с учетом различных возможных ошибок
// suspend = функция является приостановленной и может быть вызвана из корутины
// inline = функция должна быть встроена в место вызова
// reified = передать тип T в функцию и сделать его доступным во время выполнения
suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse // не принимает аргументов и возвращает объект типа HttpResponse
): Result<T, DataError.Remote> {
    val response = try {
        execute()
    } catch (e: SocketTimeoutException) { // ошибка таймаута
        return Result.Error(DataError.Remote.REQUEST_TIMEOUT)
    } catch (e: UnresolvedAddressException) { // е удалось разрешить адрес
        return Result.Error(DataError.Remote.NO_INTERNET)
    } catch (e: Exception) { // остальные исключения
        coroutineContext.ensureActive() // проверяем, активна ли корутина
        return Result.Error(DataError.Remote.UNKNOWN)
    }
    // Если выполнение функции прошло без ошибок
    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(
    response: HttpResponse // принимает объект HttpResponse и возвращает результат типа Result
): Result<T, DataError.Remote> {
    return when(response.status.value){ // проверяем значение статуса ответа HTTP
        in 200..299 -> { // статус в диапазоне 200–299 (успешные ответы)
            try { // Пытаемся получить тело ответа и вернуть успешный результат
                Result.Success(response.body<T>())
            } catch (e: NoTransformationFoundException){ // ошибка при преобразовании тела
                Result.Error(DataError.Remote.SERIALIZATION)
            }
        }
        408 -> Result.Error(DataError.Remote.REQUEST_TIMEOUT) // таймаут запроса
        429 -> Result.Error(DataError.Remote.TOO_MANY_REQUESTS) // слишком много запросов
        in 500..599 -> Result.Error(DataError.Remote.UNKNOWN) // ошибки сервера
        else -> Result.Error(DataError.Remote.UNKNOWN) // любой другой статус
    }
}