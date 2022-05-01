package com.example.books.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.books.data.entity.BookEntity
import java.util.*

@Dao
interface BooksDao {

    @Query("SELECT * FROM books")
    fun getBookList(): List<BookEntity>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBook(
        id: UUID,
    ): BookEntity

    @Insert
    suspend fun insertBook(books: BookEntity): Long

}