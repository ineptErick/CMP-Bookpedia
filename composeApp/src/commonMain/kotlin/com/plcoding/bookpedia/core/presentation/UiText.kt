package com.plcoding.bookpedia.core.presentation

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

// может представлять текстовые данные в приложении
// запечатан = все возможные реализации UiText должны быть определены в этом файле
sealed interface UiText {

    // динамическая строка
    data class DynamicString(val value: String): UiText
    // автоматически генерирует методы, такие как toString(), equals(), hashCode() и copy()
    // value будет передано при создании экземпляра класса
    // экземпляры этого класса могут быть использованы там, где ожидается UiText

    // строка из ресурсов
    class StringResourceId(
        val id: StringResource,
        val args: Array<Any> = arrayOf() // массив аргументов для форматирования строки
    ): UiText

    @Composable
    fun asString(): String { //  метод получения строки в виде текста
        // действие в завис. от (текущий экземпляр интерфейса)
        return when(this) {
            is DynamicString -> value // если объект представляет динамическую строку,
            // то вернем именно это значение
            is StringResourceId -> stringResource(resource = id, formatArgs = args) // Если StringResourceId
            // получает строку по идентификатору ресурса id, подставляя значения из массива args.
        // для получения локализованных строк из ресурсов
        }
    }
}