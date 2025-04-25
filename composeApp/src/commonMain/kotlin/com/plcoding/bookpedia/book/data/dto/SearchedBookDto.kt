package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// контейнер для информации о книге

// класс может быть сериализован (преобразован в формат, например, JSON)
// и десериализован (преобразован обратно из формата в объект)
@Serializable
// Класс данных в для хранения данных
// автоматически предоставляют методы для сравнения объектов, генерации `toString()`, `hashCode()` и `copy()`
data class SearchedBookDto(
    // поле `id` будет соответствовать ключу "key" в JSON
    @SerialName("key") val id: String, // уникальный идентификатор книги
    @SerialName("title") val title: String, // название книги
    @SerialName("language") val languages: List<String>? = null, // список языков, на которых доступна книга (может быть null)
    @SerialName("cover_i") val coverAlternativeKey: Int? = null, // альтернативный ключ для обложки книги
    @SerialName("author_key") val authorsKeys: List<String>? = null, //  хранения ключей авторов книги
    @SerialName("author_name") val authorNames: List<String>? = null, // имена авторов книги
    @SerialName("cover_edition_key") val coverKey: String? = null, // ключ издания обложки книги
    @SerialName("first_publish_year") val firstPublishYear: Int? = null, // год первого издания книги
    @SerialName("ratings_average") val ratingsAverage: Double? = null, // средний рейтинг книги
    @SerialName("ratings_count") val ratingsCount: Int? = null, // количество оценок книги
    @SerialName("number_of_pages_median") val numPagesMedian: Int? = null, // медианное количество страниц книги
    @SerialName("edition_count") val numEditions: Int? = null, // количество изданий книги
)
