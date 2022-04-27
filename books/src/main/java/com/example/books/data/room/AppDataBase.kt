package com.example.books.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.books.data.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
}