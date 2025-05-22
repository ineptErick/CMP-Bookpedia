package com.plcoding.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.book.data.database.FavoriteBookDao
import com.plcoding.bookpedia.book.data.mappers.toBook
import com.plcoding.bookpedia.book.data.mappers.toBookEntity
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import com.plcoding.bookpedia.core.domain.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// взаимодействие с удаленным источником данных и локальной базой данных для работы с книгами
class DefaultBookRepository (
    private val remoteBookDataSource: RemoteBookDataSource, // отвечающет за работу с удаленными данными (например, API для поиска книг)
    private val favoriteBookDao: FavoriteBookDao // управляет локальной базой данных (например, DAO для работы с избранными книгами).
): BookRepository { // интерфейс

    // поиск книг по заданному запросу
    override suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote> {
        return remoteBookDataSource
            .searchBooks(query) // список книг
            .map{ dto -> // каждый элемент результата (DTO) в объект Book
                dto.results.map { it.toBook() }
            }
    }

    // получение описания книги по её идентификатору
    override suspend fun getBookDescription(bookId: String): Result<String?, DataError>{
        // получить книгу из локальной базы данных по её идентификатору
        val localResult = favoriteBookDao.getFavoriteBook(bookId)
        // Если книга не найдена в локальной базе данных
        return if(localResult == null){
            // запрос к удаленному источнику данных
            remoteBookDataSource
                .getBookDetails(bookId) // Запрашиваем детали книги
                .map{it.description} // описание книги из полученных данных
        } else { //  Если книга найдена в локальной базе данных
            Result.Success(localResult.description) // успешный результат с описанием книги
        }
    }

    // получение списка избранных книг
    override fun getFavoriteBooks(): Flow<List<Book>> { // возвращает поток (Flow) списка книг
        return favoriteBookDao
            .getFavoriteBooks() // список избранных книг из базы данных
            .map{ bookEntities -> // преобразуем каждый элемент в Book
                bookEntities.map{ it.toBook()}
            }
    }

    // проверка, является ли книга избранной
    override fun isBookFavorite(id: String): Flow<Boolean>{
        return favoriteBookDao
            .getFavoriteBooks() // Получаем список избранных книг
            .map{ bookEntities ->
                bookEntities.any { it.id == id} // проверяем, есть ли среди них книга с указанным идентификатором
            }
    }

    // добавление книги в избранное
    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>{
        return try {
            favoriteBookDao.upsert(book.toBookEntity()) // Преобразует объект Book в сущность BookEntity и сохраняет её в базе данных с помощью upsert
            Result.Success(Unit) // успешный результат
        } catch(e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL) // диск заполнен
        }
    }

    //  удаление книги из избранного
    override suspend fun deleteFromFavorites(id: String){
        favoriteBookDao.deleteFavoriteBook(id)
    }
}