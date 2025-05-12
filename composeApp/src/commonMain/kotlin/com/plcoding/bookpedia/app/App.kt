package com.plcoding.bookpedia.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.plcoding.bookpedia.book.presentation.SelectedBookViewModel
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailAction
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailScreenRoot
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailViewModel
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

// основную структуру приложения с навигацией между экранами
@Composable // использоваться для построения пользовательского интерфейса
@Preview // предварительный просмотр
fun App( ) { // возвращает Uni
    MaterialTheme { // применяет тему Material Design к интерфейсу
        val navController = rememberNavController() // управляет навигацией в приложении // Создает и запоминает экземпляр
        NavHost( // определяет контейнер для навигации
            navController = navController,// принимает
            startDestination = Route.BookGraph // начальное направление
        ) {
            navigation<Route.BookGraph>( // Определяет граф навигации для BookGraph
                startDestination = Route.BookList // начальной точкой BookList
            ) {
                composable<Route.BookList>( // Определяет экран для отображения списка книг
                    // задают анимацию при выходе и входе на экран
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                    val viewModel = koinViewModel<BookListViewModel>() // Получает экземпляр ViewModel для списка книг с помощью Koin
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                    // Получает экземпляр ViewModel для выбранной книги
                    // который будет общим для всех экранов в текущем навигационном графе

                    LaunchedEffect(true) { // Запускает эффект, который будет выполнен один раз при первом составлении
                        selectedBookViewModel.onSelectBook(null) // сбрасывает выбор книги
                    }

                    BookListScreenRoot( // Отображает корневой компонент экрана списка книг
                        viewModel = viewModel,
                        onBookClick = { book -> // нажатии на книгу обновляется выбранная книга
                            selectedBookViewModel.onSelectBook(book)
                            navController.navigate( // происходит переход к экрану деталей книги
                                Route.BookDetail(book.id)
                            )
                        }
                    )
                }
                composable<Route.BookDetail>( // Определяет экран для отображения деталей книги
                    // с анимациями входа и выхода
                    enterTransition = { slideInHorizontally { initialOffset ->
                        initialOffset
                    } },
                    exitTransition = { slideOutHorizontally { initialOffset ->
                        initialOffset
                    } }
                ) {
                    val selectedBookViewModel = // Получает экземпляр ViewModel для выбранной книги
                        it.sharedKoinViewModel<SelectedBookViewModel>(navController)
                    val viewModel = koinViewModel<BookDetailViewModel>() // Получает экземпляр ViewModel для деталей книги
                    val selectedBook by selectedBookViewModel.selectedBook.collectAsStateWithLifecycle()
                    // Подписывается на состояние выбранной книги
                    // и автоматически обновляет UI при изменениях

                    LaunchedEffect(selectedBook) { // Запускает эффект при изменении выбранной книги
                        selectedBook?.let { //  Если книга выбрана
                            viewModel.onAction(BookDetailAction.OnSelectedBookChange(it)) // отправляется действие в ViewModel деталей книги
                        }
                    }

                    BookDetailScreenRoot( // Отображает корневой компонент экрана деталей книги
                        viewModel = viewModel,
                        onBackClick = { // При нажатии на кнопку "Назад"
                            navController.navigateUp() //  возврат на предыдущий экран
                        }
                    )
                }
            }
        }

    }
}

@Composable // Вспомогательная функция
// будет совместно использоваться между экранами в навигационном графе
private inline fun <reified T: ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    // Получает маршрут родительского графа навигации
    // Если его нет, создается новый экземпляр ViewModel
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()

    // Получает экземпляр NavBackStackEntry для родительского маршрута
    // чтобы использовать его как владельца хранилища ViewModel.
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel( // Возвращает экземпляр ViewModel
        // используя родительский элемент как владельца хранилища.
        viewModelStoreOwner = parentEntry
    )
}