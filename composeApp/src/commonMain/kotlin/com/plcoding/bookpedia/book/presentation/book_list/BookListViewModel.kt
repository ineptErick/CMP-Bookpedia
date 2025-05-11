@file:OptIn(FlowPreview::class)

package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.onError
import com.plcoding.bookpedia.core.domain.onSuccess
import com.plcoding.bookpedia.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// basic screen structure - управляет состоянием списка книг

// При каждом действии состояние обновляется,
// позволяет UI автоматически реагировать на изменения.
class BookListViewModel(
    private val bookRepository: BookRepository // конструктор
    // используется для доступа к данным о книгах
    // доступен только внутри этого класса
) : ViewModel() { // наследование позволяет сохранять состояние при изменении конфигурации (например, при повороте экрана)

    private var cachedBooks = emptyList<Book>() // для хранения результатов поиска
    // для управления корутинами, отвечающими за выполнение асинхронных задач:
    private var searchJob: Job? = null
    private var observeFavoriteJob: Job? = null

    private val _state = MutableStateFlow(BookListState()) // хранит текущее состояние BookListState
    //  StateFlow — это поток данных, который может быть наблюдаемым и изменяемым
    val state = _state // state предоставляет доступ к _state, добавляя onStart - выполняется при запуске потока
        .onStart {
            if(cachedBooks.isEmpty()) { // Если кэш книг пуст
                observeSearchQuery() // запускается наблюдение за поисковым запросом и любимыми книгами
            }
            observeFavoriteBooks()
        }
        .stateIn( // преобразует поток в состояние, которое будет активным, пока есть подписчики
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L), // поток будет оставаться активным в течение 5000 миллисекунд после последнего подписчика
            _state.value
        )

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> { // Если действие — это клик по книге

            }

            is BookListAction.OnSearchQueryChange -> { // Если действие связано с изменением поискового запроса
                _state.update { //  обновляется состояние
                    // копируя текущее состояние и изменяя поле searchQuery
                    it.copy(searchQuery = action.query)
                }
            }

            is BookListAction.OnTabSelected -> { // если выбрана вкладка
                _state.update { // обновляется индекс выбранной вкладки в состоянии
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    // наблюдение за любимыми книгами из репозитория
    private fun observeFavoriteBooks() {
        // Если уже есть работа (observeFavoriteJob)
        observeFavoriteJob?.cancel() // она отменяется
        observeFavoriteJob = bookRepository // Запрашиваются любимые книги из репозитория
            .getFavoriteBooks()
            .onEach { favoriteBooks ->
                _state.update { it.copy( // каждом обновлении состояния они сохраняются в _state
                    favoriteBooks = favoriteBooks
                ) }
            }
            .launchIn(viewModelScope)
    }

// создает поток, который отслеживает изменения в поисковом запросе
    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged() // фильтруя дубликаты
            .debounce(500L) // добавляя задержку в 500 мс
            .onEach { query ->
                when {
                    query.isBlank() -> { // Если запрос пустой
                        _state.update { // состояние обновляется
                            it.copy(
                                errorMessage = null, // убирая сообщение об ошибке
                                searchResults = cachedBooks // показывая кэшированные книги
                            )
                        }
                    }

                    query.length >= 2 -> { // Если длина запроса больше или равна 2 символам
                        searchJob?.cancel() // предыдущая работа поиска отменяется
                        searchJob = searchBooks(query) // запускается новая
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    //  корутина для выполнения поиска книг по запросу
    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update { //  обновляется состояние
            it.copy(
                isLoading = true // устанавливая флаг загрузки
            )
        }
        bookRepository // асинхронный запрос к репозиторию
            .searchBooks(query) // для получения результатов поиска
            .onSuccess { searchResults -> // Если поиск успешен
                _state.update { // состояние обновляется
                    it.copy(
                        isLoading = false, // флаг загрузки устанавливается в false
                        errorMessage = null,
                        searchResults = searchResults // с результатами поиска
                    )
                }
            }
            .onError { error -> // Если произошла ошибка во время поиска
                _state.update { // состояние обновляется
                    it.copy(
                        searchResults = emptyList(), // с пустым списком результатов
                        isLoading = false, // устанавливается флаг загрузки в false
                        errorMessage = error.toUiText() // отображается сообщение об ошибке
                    )
                }
            }
    }
}