CMP-Bookpedia is a cross-platform application designed to help users explore, manage, and organize their book collection. 

![Bookpedia](https://github.com/user-attachments/assets/52cc261f-7318-4b05-bd7f-f9a6778a7df7)
![Bookpedia_book](https://github.com/user-attachments/assets/c7cd3412-d0ba-413c-a772-ba2abe1977cb)
![Bookpedia_favorites](https://github.com/user-attachments/assets/73bcde0c-6314-4518-b64f-86ad098ddb6d)

Technologies used: Kotlin, Kotlin Multiplatform, Jetpack Compose. The app runs on Android, iOS, and Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.
