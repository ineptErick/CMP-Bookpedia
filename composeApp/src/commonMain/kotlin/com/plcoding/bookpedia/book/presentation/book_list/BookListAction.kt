package com.plcoding.bookpedia.book.presentation.book_list

import com.plcoding.bookpedia.book.domain.Book

// sealed = limited options here
// запечатанный интерфейс BookListAction, который может принимать три различных действия:
//1. OnSearchQueryChange — действие, связанное с изменением поискового запроса.
//2. OnBookClick — действие, связанное с кликом по книге.
//3. OnTabSelected — действие, связанное с выбором вкладки.

//Каждое из этих действий имеет свои свойства,
// которые могут быть использованы для передачи необходимой информации
// при выполнении действий в приложении.

// Использование запечатанного интерфейса позволяет легко обрабатывать
// эти действия с помощью конструкции when, обеспечивая безопасность
// типов и ясность кода.
sealed interface BookListAction {

    data class OnSearchQueryChange(val query: String): BookListAction
    data class OnBookClick(val book: Book): BookListAction
    data class OnTabSelected(val index: Int): BookListAction

}