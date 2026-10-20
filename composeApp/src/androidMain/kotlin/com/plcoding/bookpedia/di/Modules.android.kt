package com.plcoding.bookpedia.di

import com.plcoding.bookpedia.book.data.database.DatabaseFactory
import com.plcoding.bookpedia.core.presentation.LocaleManager
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single { DatabaseFactory(androidApplication()) }
        single<Settings> {
            val sharedPrefs = androidApplication().getSharedPreferences("bookpedia_settings", 0)
            SharedPreferencesSettings(sharedPrefs)
        }
        single { LocaleManager() }
    }
