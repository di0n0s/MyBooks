package com.example.books.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.books.data.entity.BookEntity

@Dao
interface BooksDao {

    @Query("SELECT * FROM books")
    fun getPagedBookList(): PagingSource<Int, BookEntity>

    @Query("SELECT * FROM books")
    fun getBookList(): List<BookEntity>

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBook(
        id: String,
    ): BookEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(books: List<BookEntity>)

    @Query("DELETE FROM books")
    suspend fun clearAll()
}