package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.presentation.UiText

// класс данных BookListState используется для хранения состояния списка книг
// с различными параметрами, такими как поисковый запрос, результаты поиска,
// любимые книги, состояние загрузки, индекс выбранной вкладки
// и сообщение об ошибке
data class BookListState(

    val searchQuery: String = "Kotlin",
    val searchResults: List<Book> = emptyList(),
    val favouriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null

)
