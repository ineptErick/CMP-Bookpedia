package com.plcoding.bookpedia.core.presentation

import com.plcoding.bookpedia.core.domain.Language
import java.util.Locale

actual class LocaleManager {
    actual fun getSystemLanguage(): Language {
        val languageCode = Locale.getDefault().language
        // Default to Russian if system is not English
        return if (languageCode == "en") Language.ENGLISH else Language.RUSSIAN
    }
}
