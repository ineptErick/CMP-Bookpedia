package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto( // класс предназначен для хранения ответа на запрос поиска
    @SerialName("docs") val results: List<SearchedBookDto> // результаты поиска — список книг, которые соответствуют запросу
    // Каждый элемент списка — это экземпляр класса SearchedBookDto, который мы рассматривали ранее и который содержит информацию о каждой книге
)
