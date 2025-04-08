package com.plcoding.bookpedia.book.presentation.book_list.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.close_hint
import cmp_bookpedia.composeapp.generated.resources.search_hint
import com.plcoding.bookpedia.core.presentation.DarkBlue
import com.plcoding.bookpedia.core.presentation.DesertWhite
import com.plcoding.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource

// создает текстовое поле для ввода поискового запроса книги
// позволяет пользователям вводить текст, выполнять поиск и очищать поле ввода

@Composable
fun BookSearchBar(
    searchQuery: String, // текущее значение текста в строке поиска
    onSearchQueryChange: (String) -> Unit, // функция, вызывается при изменении текста в строке поиска.
                                           // Она принимает новую строку как аргумент.
    onImeSearch: () -> Unit, // функция, вызывается при нажатии кнопки "Поиск" на клавиатуре.
    modifier: Modifier = Modifier // модификатор для настройки внешнего вида и поведения компонента.
) {
    // устанавливает локальные цвета для выделения текста
    // (например, при копировании или выделении)
    CompositionLocalProvider(
        LocalTextSelectionColors provides TextSelectionColors(
            // используются цвета SandYellow для фона и ручки выделения
            handleColor = SandYellow,
            backgroundColor = SandYellow
        )
    ) {
        // компонент текстового поля с обводкой
        OutlinedTextField(
            value = searchQuery, // текущее значение текстового поля
            onValueChange = onSearchQueryChange, //обработчик изменения текста
            shape = RoundedCornerShape(100), // форма текстового поля (округленная)
            colors = OutlinedTextFieldDefaults.colors( // настройки цветов
                cursorColor = DarkBlue, // цвет курсора
                focusedBorderColor = SandYellow // цвет границы при фокусировке
            ),
            // задается текст-подсказка, который отображается, когда поле пустое.
            placeholder = {
                Text(
                    text = stringResource(Res.string.search_hint) // текст берется из ресурсов (common main, comp res, values, strings)
                )
            },
            // Иконка поиска
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    // отображается слева от текстового поля
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.66f)
                )
            },
            singleLine = true,
            // Определяются действия клавиатуры.
            keyboardActions = KeyboardActions(
                // При нажатии кнопки "Поиск" будет вызвана функция onImeSearch.
                onSearch = {
                    onImeSearch()
                }
            ),
            keyboardOptions = KeyboardOptions(
                // клавиатура должна быть текстовой и содержать действие "Поиск"
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            // Иконка закрытия
            trailingIcon = {
                AnimatedVisibility(
                    // отображается справа от текстового поля, если в строке поиска есть текст.
                    visible = searchQuery.isNotBlank()
                ) {
                    IconButton(
                        // Нажатие на нее очищает строку поиска, вызывая onSearchQueryChange("").
                        onClick = {
                            onSearchQueryChange("")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(Res.string.close_hint),
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            },
            // применяется модификатор для установки фона и минимального размера интерактивного компонента.
            modifier = modifier
                .background(
                    shape = RoundedCornerShape(100),
                    color = DesertWhite
                )
                .minimumInteractiveComponentSize()
        )
    }
}