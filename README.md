# CMP-Bookpedia

CMP-Bookpedia is a modern, cross-platform application built with **Compose Multiplatform**, designed to help users explore, manage, and organize their book collection seamlessly across **Android, iOS, and Desktop**.

## 📸 Screenshots

| Book Search | Book Details | Favorites |
| :---: | :---: | :---: |
| ![Bookpedia](https://github.com/user-attachments/assets/52cc261f-7318-4b05-bd7f-f9a6778a7df7) | ![Bookpedia_book](https://github.com/user-attachments/assets/c7cd3412-d0ba-413c-a772-ba2abe1977cb) | ![Bookpedia_favorites](https://github.com/user-attachments/assets/73bcde0c-7318-4b05-bd7f-f9a6778a7df7) |

## ✨ Features

- **Search Books:** Explore a vast collection of books using a powerful search interface.
- **Book Details:** View detailed information about each book, including description, author, and ratings.
- **Favorites:** Save your favorite books to a personal list for quick access.
- **Offline Support:** Access your favorite books even without an internet connection, powered by Room database.
- **Cross-Platform:** Consistent UI and business logic across Android, iOS, and Desktop.

## 🛠️ Tech Stack

- **UI:** [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) (Jetpack Compose)
- **Networking:** [Ktor](https://ktor.io/) for asynchronous HTTP requests.
- **Dependency Injection:** [Koin](https://insert-koin.io/) for a lightweight and pragmatic DI.
- **Local Database:** [Room](https://developer.android.com/training/data-storage/room) (KMP version) for persistent storage.
- **Image Loading:** [Coil](https://coil-kt.github.io/coil/) for efficient image loading across platforms.
- **Navigation:** [Compose Navigation](https://developer.android.com/jetpack/compose/navigation) for multiplatform routing.
- **Concurrency:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html).
- **Serialization:** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) for JSON parsing.

## 📂 Project Structure

* `/composeApp` - Shared code across all platforms.
  - `commonMain`: Shared logic, UI, and data layers.
  - `androidMain`: Android-specific implementations.
  - `iosMain`: iOS-specific implementations.
  - `desktopMain`: Desktop-specific implementations.
* `/iosApp` - Entry point for the iOS application and SwiftUI-specific code.

## 🚀 Getting Started

### Prerequisites
- **Android Studio** (Koala or later)
- **Xcode** (for iOS development)
- **JDK 17+**

### Running the App
1. Clone the repository.
2. Open the project in Android Studio.
3. Select your target (Android, iOS, or Desktop) from the run configurations.
4. Click **Run**.

## 🏛️ Architecture

The project follows **Clean Architecture** principles, separating concerns into:
- **Domain:** Core business logic and entities.
- **Data:** Repository implementations and data sources (Network/Database).
- **Presentation:** UI components and ViewModels using Compose.
