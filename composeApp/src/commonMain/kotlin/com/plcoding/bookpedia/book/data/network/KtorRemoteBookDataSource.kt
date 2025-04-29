package com.plcoding.bookpedia.book.data.network

import com.plcoding.bookpedia.book.data.dto.SearchResponseDto
import com.plcoding.bookpedia.core.domain.DataError
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

// базовый URL, который будет использоваться для всех запросов к API Open Library
private const val BASE_URL = "https://openlibrary.org"

//  класс предназначен для взаимодействия с удаленными данными о книгах через API Open Library
class KtorRemoteBookDataSource(
    // экземпляр клиента HTTP, который будет использоваться для выполнения запросов
    private val httpClient: HttpClient
): RemoteBookDataSource { // интерфейс

    override suspend fun searchBooks( //  поиск книг по запросу
        query: String, // поисковый запрос (например, название книги или имя автора)
        resultLimit: Int? // максимальное количество результатов, которые следует вернуть
    ): Result<SearchResponseDto, DataError.Remote> { // результат операции, который может быть либо успешным (содержит объект типа SearchResponseDto), либо ошибочным (содержит объект типа DataError.Remote)
        return safeCall<SearchResponseDto> { // обрабатывает выполнение блока кода и управление ошибками
            httpClient.get( // выполняет HTTP GET запрос
                urlString = "$BASE_URL/search.json" // URL для запроса. Исп интерполяция строк для вставки значения константы BASE_URL
            ) {
                parameter("q", query) // добавление параметра (q) к запросу.  q содержит поисковый запрос пользователя.
                parameter("limit", resultLimit) // добавление параметра limit, который указывает максимальное количество результатов
                parameter("language", "eng") // параметр language, устанавливающий язык поиска (в данном случае английский)
                parameter("fields", "key,title,author_name,author_key,cover_edition_key,cover_i,ratings_average,ratings_count,first_publish_year,language,number_of_pages_median,edition_count")
                // параметр fields, указывает, какие поля должны быть возвращены в ответе.
            // Тут поля, такие как ключ книги, название, авторы и т.д.
            }
        }
    }

    // параметр — идентификатор работы книги (bookWorkId)
    override suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
        // Возвращает результат выполнения блока кода с помощью функции safeCall, ожидая объект типа BookWorkDto
        return safeCall<BookWorkDto> {
            httpClient.get( // HTTP GET запрос
                urlString = "$BASE_URL/works/$bookWorkId.json" // URL для запроса с использованием идентификатора работы книги (bookWorkId) в пути
            )
        }
    }
}