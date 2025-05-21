package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.BookWorkDto
import com.plcoding.bookpedia.book.data.dto.SearchResponseDto
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

// интерфейс предназначен для работы с удаленными данными о книгах
interface RemoteBookDataSource {

    // функция может быть приостановлена и возобновлена позже. исп в контексте корутин для выполнения асинхронных операций
    // функция будет использоваться для поиска книг
    suspend fun searchBooks(
        query: String, // текстовый запрос для поиска книг
        resultLimit: Int? = null // максимальное количество результатов, которые нужно вернуть (может принимать значение null)
        // Если при вызове функции не будет передано значение, то будет использовано значение null.
    ): Result<SearchResponseDto, DataError.Remote> // обертка для обработки успешных и неуспешных результатов
    //  SearchResponseDto: Тип успешного результата. класс данных (DTO), который содержит информацию о найденных книгах
    //  DataError.Remote: Тип ошибки. Это может быть перечисление или класс, описывающий возможные ошибки при работе с удаленными данными.

    // получение деталей книги по идентификатору работы книги (уникальный идентификатор книги)
    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
} // BookWorkDto: Тип успешного результата, при успешном получении деталей книги
// DataError.Remote: Тип ошибки