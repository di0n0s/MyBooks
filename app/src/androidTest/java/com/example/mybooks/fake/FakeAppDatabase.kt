package com.example.mybooks.fake

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.books.data.entity.BookEntity
import com.example.books.data.room.BooksDao

@Database(entities = [BookEntity::class], version = 1, exportSchema = false)
abstract class FakeAppDatabase : RoomDatabase() {
    abstract fun booksDao(): BooksDao
}