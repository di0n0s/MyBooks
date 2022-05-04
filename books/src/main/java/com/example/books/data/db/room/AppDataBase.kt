package com.example.books.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.books.data.db.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
}