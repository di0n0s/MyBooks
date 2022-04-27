package com.example.books.data.source

import com.example.books.data.dto.BookDto
import com.example.books.data.dto.BookListDto

interface BooksDataSource {

    suspend fun getBookList(
        start: Int,
        loadSize: Int
    ): List<BookListDto>

    suspend fun getBook(
        id: String,
    ): BookDto
}