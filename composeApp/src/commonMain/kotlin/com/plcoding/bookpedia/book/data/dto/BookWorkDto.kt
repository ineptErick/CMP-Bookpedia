package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.Serializable

// можно сохранить или передать (например, в JSON)
//  для сериализации и десериализации класса будет использоваться BookWorkDtoSerializer
@Serializable (with = BookWorkDtoSerializer::class)
data class BookWorkDto( // объект передачи данных (DTO - Data Transfer Object)
    val description: String? = null
)