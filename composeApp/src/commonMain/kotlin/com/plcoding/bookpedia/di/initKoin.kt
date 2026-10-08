package com.plcoding.bookpedia.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Initializes Koin for dependency injection across all platforms.
 *
 * This function is the main entry point for setting up Koin in the Compose Multiplatform project.
 * It registers the [sharedModule] containing common business logic and the [platformModule]
 * for platform-specific implementations (e.g., database drivers or HTTP engines).
 *
 * @param config An optional configuration block that allows for further customization
 * of the Koin application, such as setting the Android context.
 */
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(sharedModule, platformModule)
    }
}
