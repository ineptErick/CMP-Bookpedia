package com.plcoding.bookpedia.book.presentation.book_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TitledContent(
    title: String, // заголовка для содержимого
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit // будет содержать композируемый контент
) {
    Column( // вертикальный контейнер (колонку)
        // несколько дочерних элементов, располагая их один под другим
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally // Выравнивает дочерние элементы по горизонтали в центре колонки
    ) {
        Text( // строку текста на экране
            text = title,
            style = MaterialTheme.typography.titleSmall // стиль текста из темы Material Design
        )
        content() // отобразить содержимое, которое было передано в функцию TitledContent
    }
}

