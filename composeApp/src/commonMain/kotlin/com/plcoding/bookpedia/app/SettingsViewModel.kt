package com.plcoding.bookpedia.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.bookpedia.core.domain.Language
import com.plcoding.bookpedia.core.domain.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel responsible for managing application-wide settings, such as language preferences.
 *
 * The actual locale switching is handled by [LocalAppLocale] in the UI layer,
 * so this ViewModel only persists the user's choice.
 *
 * @property settingsRepository The repository used to get and set persisted settings.
 */
class SettingsViewModel(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    /**
     * A [StateFlow] representing the currently selected [Language].
     * Initialized with [Language.RUSSIAN] as the default.
     */
    val selectedLanguage: StateFlow<Language> = settingsRepository.getLanguage()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Language.RUSSIAN
        )

    /**
     * Persists the user's language choice. The actual UI update happens
     * automatically via [LocalAppLocale] in [App].
     *
     * @param language The new [Language] to set.
     */
    fun changeLanguage(language: Language) {
        viewModelScope.launch {
            settingsRepository.setLanguage(language)
        }
    }
}