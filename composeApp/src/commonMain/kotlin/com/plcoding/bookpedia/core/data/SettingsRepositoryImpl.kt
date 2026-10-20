package com.plcoding.bookpedia.core.data

import com.plcoding.bookpedia.core.domain.Language
import com.plcoding.bookpedia.core.domain.SettingsRepository
import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepositoryImpl(
    private val settings: Settings
) : SettingsRepository {

    private val languageFlow = MutableStateFlow(loadLanguage())

    override fun getLanguage(): Flow<Language> = languageFlow.asStateFlow()

    override suspend fun setLanguage(language: Language) {
        settings.putString(KEY_LANGUAGE, language.code)
        languageFlow.value = language
    }

    private fun loadLanguage(): Language {
        val code = settings.getStringOrNull(KEY_LANGUAGE)
        return code?.let { Language.fromCode(it) } ?: Language.RUSSIAN
    }

    companion object {
        private const val KEY_LANGUAGE = "key_language"
    }
}