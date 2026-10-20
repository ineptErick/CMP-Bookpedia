package com.plcoding.bookpedia.book.data.database

import androidx.room.RoomDatabase

/**
 * Platform-specific factory for creating the Room database builder.
 * Implementations are provided in androidMain, iosMain, and desktopMain.
 */
expect class DatabaseFactory {
    fun create(): RoomDatabase.Builder<FavoriteBookDatabase>
}
