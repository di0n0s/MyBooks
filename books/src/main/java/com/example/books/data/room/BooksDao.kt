package com.example.books.data.room

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.books.data.entity.BookEntity
import com.example.books.data.entity.BookListTuple

@Dao
interface BooksDao {

    @Query("SELECT id,title FROM bookentity")
    suspend fun getBookList(): PagingSource<Int, BookListTuple>

    @Query("SELECT * FROM bookentity WHERE id IN (:id)")
    suspend fun getBook(
        id: String,
    ): BookEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg books: BookEntity)
}