package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText

// хранение состояния списка книг
data class BookListState(

    val searchQuery: String = "Kotlin", // поисковый запрос
    val searchResults: List<Book> = emptyList(), // результаты поиска
    val favouriteBooks: List<Book> = emptyList(), // любимые книги
    val isLoading: Boolean = false, // состояние загрузки
    val selectedTabIndex: Int = 0,  // индекс выбранной вкладки
    val errorMessage: UiText? = null // сообщение об ошибке
)
