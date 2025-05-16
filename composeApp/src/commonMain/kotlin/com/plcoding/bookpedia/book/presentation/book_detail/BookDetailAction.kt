package com.plcoding.bookpedia.book.presentation.book_detail

import com.plcoding.bookpedia.book.domain.Book

// определения различных действий,
// которые могут произойти в контексте деталей книги в приложении

// все возможные реализации должны находиться в одном файле
sealed interface BookDetailAction {

    data object onBackClick: BookDetailAction // действие возврата назад, реализует интерфейс
    data object onFavoriteClick: BookDetailAction // добавления или удаления книги из избранного
    data class OnSelectedBookChange(val book: Book): BookDetailAction // изменения выбранной книги с передачей объекта книги

}