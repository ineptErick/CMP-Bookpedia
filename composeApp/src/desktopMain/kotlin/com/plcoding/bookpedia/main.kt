package com.plcoding.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.plcoding.bookpedia.app.App
import com.plcoding.bookpedia.di.initKoin

// создания графического пользовательского интерфейса (GUI)
// с использованием Koin для внедрения зависимостей
// библиотеки Jetpack Compose для создания оконных приложений
fun main() { // = стандартной точкой начала выполнения программы
    initKoin() // инициализирует Koin
    application { // определяет приложение
        // используется DSL (Domain Specific Language) для настройки приложения
        // параметры и настройки для приложения

        Window( // новое окно приложения
            onCloseRequest = ::exitApplication, // когда пользователь закроет окно, приложение завершится
            title = "CMP-Bookpedia", //  заголовок установлен как "CMP-Bookpedia"
        ) {
            App() // отвечает за отрисовку интерфейса
        }
    }
}
