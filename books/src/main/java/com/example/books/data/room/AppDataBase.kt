package com.example.books.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.books.data.entity.BookEntity
import com.example.books.data.entity.RemoteKeyEntity

@Database(entities = [BookEntity::class, RemoteKeyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
    abstract fun remoteKeysDao(): RemoteKeyDao
}