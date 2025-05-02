package com.plcoding.bookpedia.book.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

// контракт для работы с книгами
interface BookRepository {

    // поиск
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    // получение описаний
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>

    // управление избранными книгами
    fun getFavoriteBooks(): Flow<List<Book>> // возвращает (поток) список любимых книг
    fun isBookFavorite(id: String): Flow<Boolean> // является ли книга избранной
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> // добавляет в избранное
    suspend fun deleteFromFavorites(id: String) // удалить из избранного
}