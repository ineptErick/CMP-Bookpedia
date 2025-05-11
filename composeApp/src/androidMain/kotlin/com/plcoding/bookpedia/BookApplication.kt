package com.plcoding.bookpedia

import android.app.Application
import com.plcoding.bookpedia.di.initKoin
import org.koin.android.ext.koin.androidContext

// настройки глобальных параметров приложения
class BookApplication: Application() { // выполнения инициализации, которая должна происходить только один раз

    override fun onCreate() { // создании экземпляра приложения
        super.onCreate()
        initKoin { // инициализации библиотеки Koin
            androidContext(this@BookApplication) // устанавливает контекст Android для Koin
            // ссылается на текущий экземпляр класса
            // нужно, чтобы Koin мог использовать контекст приложения для управления зависимостями и их жизненным циклом
        }
    }
}