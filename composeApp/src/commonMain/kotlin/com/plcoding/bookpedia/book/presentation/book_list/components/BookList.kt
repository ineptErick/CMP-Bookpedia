package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.plcoding.bookpedia.book.domain.Book

// создает вертикальный список книг с возможностью обработки кликов по каждой книге
// и применением различных визуальных модификаций к элементам списка.
@Composable
fun BookList(
    books: List<Book>, // books, которые будут отображаться в списке.
    onBookClick: (Book) -> Unit, // принимает объект Book и не возвращает значения (Unit).
    // Эта функция будет вызвана при нажатии на элемент списка.
    modifier: Modifier = Modifier, // модификаторы для настройки внешнего вида компонента
scrollState: LazyListState = rememberLazyListState()){ // хранит состояние прокрутки для списка
    // rememberLazyListState() создает и запоминает состояние прокрутки для данного списка

LazyColumn( // отображает элементы в вертикальном списке с ленивой загрузкой.
    modifier = modifier, // Передача модификатора для настройки внешнего вида LazyColumn.
    state = scrollState, // Установка состояния прокрутки для списка, чтобы управлять его поведением.
    verticalArrangement = Arrangement.spacedBy(12.dp), // между элементами списка будет отступ в 12 dp по вертикали
    horizontalAlignment = Alignment.CenterHorizontally // Центрирует элементы списка по горизонтали
){
    items( // принимает список элементов и создает для каждого элемента представление (UI)
        items = books, //  Передача списка книг для отображения.
        key = { it.id } // Указание ключа для каждого элемента списка.
        // помогает Compose отслеживать изменения в элементах списка и оптимизировать обновления UI

    ){ book -> // Лямбда-выражение, которое представляет каждый элемент списка

        BookListItem( // отображение отдельной книги в списке
            book = book, // Передача текущего объекта book в компонент BookListItem.
            modifier = Modifier // Передача модификаторов для настройки внешнего вида элемента списка:
                .widthIn(max = 700.dp) // максимальная ширина элемента до 700 dp.
                .fillMaxWidth() // Заполняет доступную ширину.
                .padding(horizontal = 16.dp), // Добавляет горизонтальные отступы по 16 dp.
            onClick = { // Установка обработчика клика для элемента списка.
                onBookClick(book) // При нажатии на элемент вызывается функция onBookClick, передавая ей текущий объект book.
            }
        )
}
}
}