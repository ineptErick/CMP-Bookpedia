package com.plcoding.bookpedia

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
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