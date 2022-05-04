package com.example.books.data.db.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.books.data.db.entity.BookEntity

@Dao
interface BooksDao {

    @Query("SELECT * FROM books  LIMIT :pageSize OFFSET :start")
    fun getBookList(start: Int, pageSize: Int): List<BookEntity>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBook(
        id: String
    ): BookEntity

    @Insert
    suspend fun insertBook(books: BookEntity): Long

}