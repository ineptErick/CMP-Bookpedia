package com.plcoding.bookpedia.core.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// object используется для создания синглтона
object HttpClientFactory {

    // создание нового клиента HTTP с заданным движком
    //  принимает параметр engine типа HttpClientEngine и возвращает экземпляр HttpClient
    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine){ // создаем новый экземпляр HttpClient, передавая ему движок (engine)
            // настраиваем клиент, добавляя различные установки (installations)
            install(ContentNegotiation){ // добавление поддержки согласования контента (Content Negotiation) в клиент
                // Это позволяет клиенту автоматически обрабатывать разные форматы данных
                json( // будем использовать JSON для согласования контента
                    json = Json { // создаем экземпляр Json
                        // при десериализации JSON-объектов клиент будет игнорировать неизвестные ключи
                        // которые не соответствуют полям целевого класса
                        ignoreUnknownKeys = true
                    }
                )
            }

            install(HttpTimeout){ // таймауты для HTTP-запросов
                socketTimeoutMillis = 20_000L // время ожидания для сокетов
                requestTimeoutMillis = 20_000L //  время ожидания для запросов
                // если соединение не будет установлено или запрос не будет выполнен в течение этого времени
                // будет выброшено исключение
            }

            //  поддержка логирования
            // позволит отслеживать и выводить информацию о запросах и ответах
            install(Logging){
                logger = object : Logger { // анонимный объект, реализующий интерфейс Logger
                    override fun log(message: String) { // просто выводит сообщение в консоль с помощью println
                        println(message)
                    }
                }
                level = LogLevel.ALL // уровень логирования
                // будут записываться все сообщения (информационные, предупреждения и ошибки)
            }
            defaultRequest { // Устанавливаем значения по умолчанию для всех исходящих запросов
                // заголовок Content-Type для всех запросов на значение application/json
                contentType(ContentType.Application.Json)
                // указываем серверу, что тело запроса будет в формате JSON
            }
        }
    }
}