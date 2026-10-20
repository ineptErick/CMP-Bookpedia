package com.plcoding.bookpedia.core.presentation

import com.plcoding.bookpedia.core.domain.Language

expect class LocaleManager {
    fun getSystemLanguage(): Language
}
