package com.plcoding.bookpedia.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.plcoding.bookpedia.book.data.database.DatabaseFactory
import com.plcoding.bookpedia.book.data.database.FavoriteBookDatabase
import com.plcoding.bookpedia.book.data.network.KtorRemoteBookDataSource
import com.plcoding.bookpedia.book.data.network.RemoteBookDataSource
import com.plcoding.bookpedia.book.data.repository.DefaultBookRepository
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.book.presentation.SelectedBookViewModel
import com.plcoding.bookpedia.book.presentation.book_detail.BookDetailViewModel
import com.plcoding.bookpedia.book.presentation.book_list.BookListViewModel
import com.plcoding.bookpedia.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

// определяет модули Koin, которые содержат различные зависимости,
// используемые в приложении

// expect: ключевое слово в Kotlin Multiplatform
// для объявления ожидаемого значения или класса,
// которое должно быть реализовано в конкретной платформе
expect val platformModule: Module // реализация этой переменной (platformModule) будет предоставлена в специфичных для платформы файлах

// модуль Koin, который может содержать определения зависимостей
val sharedModule = module {
    // регистрирует зависимость как синглтон - при каждом запросе этой зависимости будет возвращаться один и тот же экземпляр
    single { HttpClientFactory.create(get()) } // создается экземпляр HTTP-клиента с помощью фабрики HttpClientFactory
    // регистрирует синглтон для класса KtorRemoteBookDataSource
    //  :: используется для ссылки на конструктор класса
    // связываем зарегистрированный класс KtorRemoteBookDataSource с интерфейсом RemoteBookDataSource
    // позволяет Koin возвращать экземпляр KtorRemoteBookDataSource, когда запрашивается RemoteBookDataSource
    singleOf(::KtorRemoteBookDataSource).bind<RemoteBookDataSource>()
    // при запросе RemoteBookDataSource будет возвращен экземпляр DefaultBookRepository
    singleOf(::DefaultBookRepository).bind<BookRepository>()

    single {
        get<DatabaseFactory>().create() // создать новый экземпляр базы данных
            .setDriver(BundledSQLiteDriver()) // драйвер базы данных
            .build() // Завершаем создание базы данных
    } // В результате будет создана база данных, которая будет храниться как синглтон.

    // Получаем DAO (Data Access Object) для работы с любимыми книгами из базы данных.
    single { get<FavoriteBookDatabase>().favoriteBookDao }

    // Регистрирует ViewModel для BookListViewModel
    // Koin будет управлять жизненным циклом этой ViewModel и предоставлять ее экземпляр по запросу
    viewModelOf(::BookListViewModel)
    viewModelOf(::BookDetailViewModel)
    viewModelOf(::SelectedBookViewModel)

    // В результате этого кода создается модуль Koin,
// который содержит определения различных зависимостей и ViewModel'ей,
// используемых в приложении.
}
