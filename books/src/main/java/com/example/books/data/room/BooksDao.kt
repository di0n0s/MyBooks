package com.example.books.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.books.data.entity.BookEntity
import com.example.books.data.entity.BookListTuple

@Dao
interface BooksDao {

    @Query("SELECT id,title FROM bookentity")
    suspend fun getBookList(
        start: Int,
        loadSize: Int
    ): List<BookListTuple>

    @Query("SELECT * FROM bookentity WHERE id IN (:id)")
    suspend fun getBook(
        id: String,
    ): BookEntity

    @Insert
    suspend fun insertAll(vararg books: BookEntity)
}