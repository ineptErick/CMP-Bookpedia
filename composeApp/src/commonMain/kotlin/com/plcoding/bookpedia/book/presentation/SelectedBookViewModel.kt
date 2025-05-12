package com.plcoding.bookpedia.book.presentation

import androidx.lifecycle.ViewModel
import com.plcoding.bookpedia.book.domain.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

// управления состоянием выбранной книги
class SelectedBookViewModel: ViewModel() { // сохранять состояние при изменениях конфигурации

    // Использование подчеркивания в начале имени переменной является общепринятой практикой для обозначения "приватной" переменной
    private val _selectedBook = MutableStateFlow<Book?>(null) // будет хранить состояние выбранной книги
    // значение может отсутствовать (в данном случае, когда книга не выбрана)

    val selectedBook = _selectedBook.asStateFlow() // получить неизменяемую версию состояния
    //  внешние классы могут только читать состояние выбранной книги, но не могут его изменять напрямую

    fun onSelectBook(book: Book?){ //  позволяет передавать как выбранную книгу, так и значение `null`, если книга не выбрана
        // Если передан объект книги, `_selectedBook` будет обновлен на этот объект
        _selectedBook.value = book // устанавливаем значение `_selectedBook` равным переданному параметру `book`
    }

}