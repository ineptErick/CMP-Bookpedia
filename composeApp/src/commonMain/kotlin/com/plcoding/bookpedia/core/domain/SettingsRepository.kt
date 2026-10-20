package com.plcoding.bookpedia.core.domain

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getLanguage(): Flow<Language>
    suspend fun setLanguage(language: Language)
}
