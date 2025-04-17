package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.plcoding.bookpedia.core.presentation.DarkBlue
import org.koin.compose.viewmodel.koinViewModel

//  этот код создает экран списка книг с использованием Jetpack Compose,
//  управляя состоянием через ViewModel и обрабатывая действия пользователей

@Composable // использована для построения пользовательского интерфейса
fun BookListScreenRoot(
    // Получение экземпляра BookListViewModel через Koin (библиотека для внедрения зависимостей).
    viewModel: BookListViewModel = koinViewModel(), // позволяет управлять состоянием и логикой приложения.
    onBookClick: (Book) -> Unit
){
    // Используется для подписки на состояние из ViewModel.
    val state by viewModel.state.collectAsStateWithLifecycle()
    // collectAsStateWithLifecycle() обеспечивает автоматическое управление жизненным циклом,
    // состояние будет обновляться в зависимости от состояния компонента.

    // Вызов другого композируемого компонента BookListScreen
    BookListScreen(
        // передает ему текущее состояние и обработчик действий
        state = state,
        onAction = { action ->
            when(action){
                is BookListAction.OnBookClick -> onBookClick(action.book)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun BookListScreen(
    // принимает текущее состояние и обработчик действий
    state: BookListState,
    onAction: (BookListAction) -> Unit,
) {
    // Получение текущего контроллера программной клавиатуры
    // чтобы можно было управлять её состоянием (например, скрывать клавиатуру)
    val keyboardController = LocalSoftwareKeyboardController.current

    // Создание вертикального контейнера для размещения дочерних компонентов.
    Column(
        modifier = Modifier
            .fillMaxSize() // заполняет весь доступный размер
            .background(DarkBlue) // устанавливает фон
            .statusBarsPadding(), // добавляет отступы для статус-бара
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Вызов компонента строки поиска
        BookSearchBar(
            searchQuery = state.searchQuery, // текущее значение строки поиска из состояния
            onSearchQueryChange = { // обработчик изменения текста в строке поиска
                // вызывает onAction с действием OnSearchQueryChange
                onAction(BookListAction.OnSearchQueryChange(it))
            },
            // обработчик нажатия кнопки "Поиск" на клавиатуре
            onImeSearch = {
                // скрывает клавиатуру
                keyboardController?.hide()
            },
            // модификатор для настройки внешнего вида строки поиска
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}