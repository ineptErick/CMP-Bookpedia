package com.plcoding.bookpedia.core.domain

enum class Language(val code: String) {
    ENGLISH("en"),
    RUSSIAN("ru");

    companion object {
        fun fromCode(code: String): Language {
            return entries.find { it.code == code } ?: RUSSIAN
        }
    }
}
