/**
 * Desktop-specific entry point for the CMP-Bookpedia application.
 *
 * This file handles the bootstrapping of the JVM target, including:
 * - Initializing Koin for dependency injection.
 * - Setting up the Compose Multiplatform application lifecycle.
 * - Configuring and displaying the primary application window.
 */
package com.plcoding.bookpedia

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.plcoding.bookpedia.app.App
import com.plcoding.bookpedia.di.initKoin

/**
 * The main entry point for the Desktop version of the Bookpedia application.
 *
 * This function performs the following steps:
 * 1. **Dependency Injection**: Initializes the Koin framework via [initKoin].
 *    This sets up shared business logic and desktop-specific platform configurations.
 * 2. **Application Scope**: Starts the Compose Multiplatform application lifecycle using [application].
 * 3. **Window Management**: Creates and configures the main application [Window].
 *    - `onCloseRequest = ::exitApplication`: Ensures the process terminates when the window is closed.
 *    - `title = "CMP-Bookpedia"`: Sets the title displayed in the window's title bar.
 * 4. **UI Composition**: Renders the root [App] composable, which serves as the 
 *    entry point for the shared UI and navigation logic.
 */
fun main() {
    // Initialize dependency injection for the shared and platform modules
    initKoin()

    application {
        // Defines the primary window for the desktop app
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMP-Bookpedia",
        ) {
            // Launches the root Composable of the application
            App()
        }
    }
}
