package com.plcoding.bookpedia.di

import com.plcoding.bookpedia.book.data.database.DatabaseFactory
import com.plcoding.bookpedia.core.presentation.LocaleManager
import com.russhwolf.settings.PreferencesSettings
import com.russhwolf.settings.Settings
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.dsl.module
import java.util.prefs.Preferences

/**
 * Desktop-specific dependencies.
 * Provides the HTTP engine, database factory, and persistent settings for the JVM target.
 */
actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { DatabaseFactory() }
        single<Settings> { PreferencesSettings(Preferences.userRoot()) }
        single { LocaleManager() }
    }
