package com.plcoding.bookpedia.book.presentation.book_detail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.plcoding.bookpedia.core.presentation.LightBlue

enum class ChipSize { // размер чипа
    SMALL, REGULAR
}

@Composable
fun BookChip(
    modifier: Modifier = Modifier, // внешний вид компонента
    size: ChipSize = ChipSize.REGULAR, // размер чипа и по умолчанию
    chipContent: @Composable RowScope.() -> Unit // позволяет использовать функции компоновки для размещения элементов в строке
) {
    Box( // контейнер, может содержать один дочерний элемент и позволяет накладывать элементы друг на друга
        modifier = modifier
            .widthIn( // минимальную ширину для Box
                min = when(size){
                    ChipSize.SMALL -> 50.dp //  50.dp (для SMALL)
                    ChipSize.REGULAR -> 80.dp // 80.dp (для REGULAR)
                }
            )
            .clip(RoundedCornerShape(16.dp)) // закругленные углы с радиусом 16.dp
            .background(LightBlue)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp
            ),
        contentAlignment = Alignment.Center // Выравнивает по центру
    ){
        Row( // строка (горизонтальный контейнер)
            verticalAlignment = Alignment.CenterVertically, // дочерние элементы по вертикали в центре
            horizontalArrangement = Arrangement.Center // дочерние элементы по горизонтали в центре
        ){
            chipContent() // отобразить содержимое чипа внутри строки
        }
    }
}