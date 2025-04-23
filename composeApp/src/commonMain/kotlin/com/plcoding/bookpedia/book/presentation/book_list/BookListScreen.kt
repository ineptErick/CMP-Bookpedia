package com.plcoding.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.favorites
import cmp_bookpedia.composeapp.generated.resources.no_favorite_books
import cmp_bookpedia.composeapp.generated.resources.no_search_results
import cmp_bookpedia.composeapp.generated.resources.search_results
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.components.BookList
import com.plcoding.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
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

    // будет хранить состояние для компонента Pager
    val pagerState = rememberPagerState { 2 }
    //  rememberPagerState, чтобы запомнить состояние пейджера между перерисовками.
    //  параметр = лямбда, возвращает значение 2 (т.е. изначально активен третий элемент)

    // состояние, содержит результаты поиска
    // Когда оно изменяется, запоминается состояние
    val searchResultsListState = rememberLazyListState()

    // Создает и запоминает состояние списка книг
    // = информация о текущем положении прокрутки и позволяет управлять прокруткой списка
    val favoriteBooksListState = rememberLazyListState()

    // эффект, который запускается при изменении указанных зависимостей
    // = когда изменяется state.searchResults, будет выполнен код внутри блока
    LaunchedEffect(state.searchResults){
        // состояние прокрутки для списка результатов поиска
        searchResultsListState.animateScrollToItem(0)
        // функция анимирует прокрутку списка до первого элемента (индекс 0)
    // когда результаты поиска обновляются, список плавно прокручивается к началу
    }

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

        Surface( // фон и форма для других элементов
            modifier = Modifier // модификатор,
                // заставляет Surface занимать доступное пространство по вертикали (вес 1)
                // и полностью заполнять ширину.
                .weight(1f)
                .fillMaxWidth(),
            color = DesertWhite,
            shape = RoundedCornerShape( //  углы сверху будут скруглены на 32.dp
                topStart = 32.dp,
                topEnd = 32.dp
            )
        ){
            Column( // располагает свои дочерние элементы вертикально
                horizontalAlignment = Alignment.CenterHorizontally
                // Выравнивание дочерних элементов по горизонтали по центру
            ) {
                TabRow( // отображает набор вкладок
                    selectedTabIndex = state.selectedTabIndex, // Указывает, какая вкладка выбрана, используя индекс из состояния
                    modifier = Modifier
                        // Задает отступы по вертикали в 12.dp
                        // и устанавливает максимальную ширину в 700.dp
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth(),
                    containerColor = DesertWhite,
                    indicator = { tabPositions -> // индикатор для выбранной вкладки
                        TabRowDefaults.SecondaryIndicator( // Создает индикатор для выбранной вкладки с заданным цветом и модификатором
                            color = SandYellow,
                            modifier = Modifier
                                .tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                            // смещение индикатора в зависимости от выбранной вкладки
                        )
                    }
                ){
                    Tab( // вкладка
                        // Проверяет, выбрана ли первая вкладка
                        selected = state.selectedTabIndex == 0,
                        onClick = { // Обработчик клика
                            //  вызывает действие при выборе вкладки
                            onAction(BookListAction.OnTabSelected(0))
                        },
                        // чтобы вкладка занимала равное пространство с другими вкладками
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        // Цвет текста для невыбранной вкладки с прозрачностью
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ){
                        // Внутри Tab:
                        Text( // текст внутри вкладки
                            text = stringResource(Res.string.search_results),
                            modifier = Modifier
                                .padding(vertical = 12.dp) // вертикальные отступы для текста
                        )
                    }
                    Tab( // вторая вкладка
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(BookListAction.OnTabSelected(1))
                        },
                        modifier = Modifier.weight(1f),
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = stringResource(Res.string.favorites),
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                        )
                    }
                }
                // создает пространство между другими элементами
                Spacer(modifier = Modifier.height(4.dp)) // между предыдущим и следующим элементами будет отступ в 4 единицы

                HorizontalPager( // позволяет отображать контент в виде горизонтально прокручиваемых страниц
                    state = pagerState, // Передает состояние пейджера, которое было создано ранее. Состояние управляет текущей страницей, анимацией и т.д.
                    modifier = Modifier
                        .fillMaxWidth() // заполнение всей доступной ширины
                        .weight(1f) // занимает оставшееся пространство в родительском контейнере
                ){ pageIndex -> // принимает индекс текущей страницы (от 0 до n) и определяет, что будет отображаться на этой странице
                    Box( // располагает свои дочерние элементы друг на друге
                        modifier = Modifier
                            .fillMaxSize(), // заполнение всего доступного пространства
                        contentAlignment = Alignment.Center // выровнено по центру
                    ) {
                        when(pageIndex){ //  выбор действия в зависимости от значения pageIndex
                            0 -> { // если 0
                                if(state.isLoading){ // находится ли состояние в процессе загрузки
                                    // Если состояние загрузки активно, отображает индикатор загрузки (крутящийся круг).
                                    CircularProgressIndicator()
                                } else {
                                    // Обработка состояния после загрузки
                                    when {
                                        state.errorMessage != null -> { // Если есть ошибка (т.е. сообщение об ошибке не равно null), отображается текст ошибки
                                            Text(
                                                text = state.errorMessage.asString(), // сообщение об ошибке = строка
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        state.searchResults.isEmpty() -> { // Обработка пустого результата поиска
                                            Text( // проверяется, пуст ли список результатов поиска.
                                                text = stringResource(Res.string.no_search_results),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                        else -> { // Если нет ошибок и результаты не пусты, отображается список книг
                                            BookList(
                                                books = state.searchResults, //  Передает список найденных книг в компонент BookList
                                                onBookClick = { // Обработчик клика по книге
                                                    onAction(BookListAction.OnBookClick(it)) // передаем id книги
                                                },
                                                modifier = Modifier.fillMaxSize(),
                                                scrollState = searchResultsListState // Передает состояние прокрутки для списка книг
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> { // если 1
                                if(state.favouriteBooks.isEmpty()){ // Если список избранных книг пустой, отображается текстовое сообщение.
                                    Text(
                                        text = stringResource(Res.string.no_favorite_books),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                    )
                                } else { // Если список избранных книг не пустой, выводит список книг в сост favouriteBooks
                                    BookList(
                                        books = state.favouriteBooks,
                                        // обработчик клика по книге
                                        // Когда книга нажата, вызывается функция onAction, передавая действие OnBookClick с идентификатором книги (it).
                                        onBookClick = {
                                            onAction(BookListAction.OnBookClick(it))
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        // состояние прокрутки для управления прокруткой списка книг
                                        scrollState = favoriteBooksListState
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

