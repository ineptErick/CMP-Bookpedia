package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import com.plcoding.bookpedia.book.data.network.KtorRemoteBookDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// this is a very basic screen structure
// `BookListViewModel`, который управляет состоянием списка книг.

// Он использует `MutableStateFlow` для хранения состояния
// и предоставляет функцию `onAction` для обработки
// различных действий (клик по книге, изменение поискового запроса и выбор вкладки).

// При каждом действии состояние обновляется соответствующим образом,
// что позволяет UI автоматически реагировать на изменения.
class BookListViewModel(
    private val dataSource: KtorRemoteBookDataSource
): ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

    fun onAction(action: BookListAction) {

        when(action){
            is BookListAction.OnBookClick -> {

            }
            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }

    }

}