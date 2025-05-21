package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.Serializable

// автоматически генерировать необходимый код для преобразования объектов класса в JSON
@Serializable
data class DescriptionDto(
    val value: String
)