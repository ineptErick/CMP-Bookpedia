package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.bookpedia.app.Route
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//  управления состоянием экрана деталей книги
class BookDetailViewModel (

    private val bookRepository: BookRepository, // отвечающий за доступ к данным о книгах (получение, сохранение, удаление книг).
    private val savedStateHandle: SavedStateHandle //  сохранения и восстановления состояния при изменении конфигурации

): ViewModel() { // позволяет сохранять состояние при изменении конфигурации и управлять жизненным циклом

    // будет хранить идентификатор книги
    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id // получения маршрута (route) типа `BookDetail`, содержит информацию о текущем экране

    private val _state = MutableStateFlow(BookDetailState()) // будет хранить текущее состояние экрана деталей книги
    val state = _state // будет предоставлять доступ к текущему состоянию
        .onStart { // выполнения действий при первом подписывании на состояние
            fetchBookDescription() // загружает описание книги
            observeFavoriteStatus() // наблюдает за статусом избранного для книги
        }
        .stateIn( // Преобразует поток состояния в состояние, которое будет автоматически обновляться
            viewModelScope, // Область видимости корутин, которая автоматически отменяется при уничтожении ViewModel
            // поток будет активен, пока на него есть подписчики. Если подписчиков нет в течение 5 секунд, поток будет остановлен
            SharingStarted.WhileSubscribed(5000L),
            _state.value // Начальное значение состояния
        )

    fun onAction(action: BookDetailAction) { // различные действия пользователя
        when(action) {
            is BookDetailAction.OnSelectedBookChange -> { // является ли действие изменением выбранной книги
                _state.update { it.copy( // Обновляет текущее состояние, создавая копию с новым значением
                    book = action.book
                ) }
            }
            is BookDetailAction.OnFavoriteClick -> { // является ли действие нажатием на кнопку "Избранное"
                viewModelScope.launch { // Запускает новую корутину
                    if(state.value.isFavorite) { //  является ли книга избранной
                        bookRepository.deleteFromFavorites(bookId) // да, удаления книги из избранного
                    } else {
                        state.value.book?.let { book -> // нет, проверяет, существует ли книга (`state.value.book`)
                            bookRepository.markAsFavorite(book) //  добавления книги в избранное
                        }
                    }
                }
            }
            // если не соответствует ни одному из обработанных случаев
            else -> Unit // то ничего не делает
        }
    }

    private fun observeFavoriteStatus(){ // метод для наблюдения за статусом избранного книги
        bookRepository
            .isBookFavorite(bookId) // проверки, является ли книга избранной
            .onEach { isFavorite -> // Для каждого изменения статуса избранного обновляет состояние
                _state.update { it.copy( // Обновляет состояние
                    isFavorite = isFavorite // с новым значением поля `isFavorite`
                ) }
            }
            .launchIn(viewModelScope) // Запускает наблюдение в области видимости ViewModel
    }

    private fun fetchBookDescription(){ // метод для получения описания книги
        viewModelScope.launch { // Запускает новую корутину для асинхронного выполнения
            bookRepository
                .getBookDescription(bookId) // получения описания книги по ее идентификатору
                .onSuccess { description -> // Если запрос успешен
                    _state.update { it.copy( // обновляет состояние с новым описанием книги
                        book = it.book?.copy( // создания новой копии объекта книги
                            description = description // с обновленным описанием
                        ),
                        isLoading = false // завершение загрузки
                    ) }
                }
        }
    }
}

