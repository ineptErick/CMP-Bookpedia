package com.plcoding.bookpedia.core.presentation

import com.plcoding.bookpedia.core.domain.Language
import platform.Foundation.NSLocale
import platform.Foundation.languageCode
import platform.Foundation.currentLocale

actual class LocaleManager {
    actual fun getSystemLanguage(): Language {
        val languageCode = NSLocale.currentLocale.languageCode
        return if (languageCode == "en") Language.ENGLISH else Language.RUSSIAN
    }
}
