package com.plcoding.bookpedia.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

// инициализации Koin — библиотеки для внедрения зависимостей в приложениях на Kotlin
// принимает один параметр config
// Тип параметра — KoinAppDeclaration?
// значение по умолчанию null, позволяет вызывать функцию без передачи аргумента
fun initKoin(config: KoinAppDeclaration? = null){
    startKoin{ // инициализация Koin
        // config не равен null, вызывается метод invoke на объекте config, передавая в него текущий контекст Koin
        // позволяет пользователю передать свою собственную конфигурацию для настройки Koin, если он этого хочет
        config?.invoke(this) //  (?.) для проверки, не является ли config равным null

        modules(sharedModule, platformModule) // добавляет модули в Koin
        // sharedModule и platformModule — переменные, которые должны содержать определения модулей Koin
    // различные зависимости и их реализации, которые Koin будет управлять
    }
}