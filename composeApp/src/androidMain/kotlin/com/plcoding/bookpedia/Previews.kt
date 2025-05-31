package com.plcoding.bookpedia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreen
import com.plcoding.bookpedia.book.presentation.book_list.BookListState
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar

// код создает предварительный просмотр компонента строки поиска книг (BookSearchBar)
// с белым фоном и занимает всю ширину экрана

@Preview
@Composable
private fun BookSearchBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Параметры для строки поиска и обработчики событий инициализируются пустыми значениями,
        // это позволяет визуализировать компонент без необходимости взаимодействия с ним
        BookSearchBar(
            searchQuery = "",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun BookListScreenPreview(){ // отображения предварительного просмотра экрана списка книг
    BookListScreen( //  отображение экрана со списком книг
        state = BookListState( // создается новый экземпляр BookListState
            // содержит данные, необходимые для отображения списка книг
            searchResults = books // передаем список книг, который будет отображаться на экране.
        ),
        onAction = {} // обработчик действий.
    // Это может быть использовано для обработки событий,
    // таких как нажатия кнопок или другие взаимодействия с пользователем.
    // Поскольку здесь передается пустая функция, это означает,
    // что никаких действий не будет выполнено при взаимодействии.
    )
}
//  список книг.
//  создается с помощью функции map, которая применяется к диапазону чисел от 1 до 100.
private val books = (1..100).map {
    Book( // Вызов конструктора класса Book для создания нового объекта книги.
        id = it.toString(), // Установка идентификатора книги. it — это текущее значение из диапазона (от 1 до 100), и оно преобразуется в строку.
        title = "Book $it", // Установка названия книги. Название формируется с использованием интерполяции строк, где $it заменяется на текущее значение из диапазона.
        imageUrl = "https://test.com", // Установка URL изображения для книги. В данном случае он статичен и указывает на один и тот же адрес для всех книг.
        authors = listOf("Philipp Lackner"), // Установка списка авторов книги. В данном случае каждый объект книги имеет одного автора с именем "Philipp Lackner".
        description = "Description $it", // Установка описания книги с интерполяцией строки, где $it заменяется на текущее значение из диапазона.
        languages = emptyList(), // Установка списка языков книги. Здесь используется пустой список, что означает, что информация о языках не указана.
        firstPublishYear = null, // Установка года первого издания книги в значение null, что может означать, что эта информация недоступна.
        averageRating = 4.67854, // Установка среднего рейтинга книги. Значение задано явно и равно 4.67854.
        ratingCount = 5, // Установка количества оценок для книги, равного 5.
        numPages = 100, //  Установка количества страниц в книге, равного 100.
        numEditions = 3 // Установка количества изданий книги, равного 3.
    )
}