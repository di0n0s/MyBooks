package com.example.books.data.source

import com.example.books.domain.model.Book

interface BooksDataSource {

    suspend fun getBookList(
        start: Int,
        loadSize: Int
    ): List<Book>

    suspend fun getBook(
        id: String,
    ): Book

}