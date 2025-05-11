package com.plcoding.bookpedia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.plcoding.bookpedia.app.App
import io.ktor.client.engine.okhttp.OkHttp

// создания пользовательского интерфейса
class MainActivity : ComponentActivity() { // основную активность приложения, которая запускается при его старте
    override fun onCreate(savedInstanceState: Bundle?) { // создании активности
        super.onCreate(savedInstanceState) // обеспечивает корректную инициализацию активности и ее компонентов.
        // Без этого вызова могут возникнуть проблемы с работой активности.

        setContent { // установки содержимого пользовательского интерфейса активности
            App() // отрисовку основного интерфейса приложения
        }
    }
}
