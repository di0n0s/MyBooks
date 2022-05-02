package com.example.books.data.source

import com.example.books.data.entity.BookEntity
import com.example.books.domain.model.Book

interface BooksDataSource {

    suspend fun getBookList(
        loadSize: Int
    ): List<Book>

    suspend fun getBook(
        id: String,
        isForList: Boolean
    ): Book

    suspend fun insertBook(book: BookEntity): Boolean

}