package com.example.books.data.source

import com.example.books.domain.model.Book
import java.util.*

interface BooksDataSource {

    suspend fun getBookList(
        loadSize: Int
    ): List<Book>

    suspend fun getBook(
        id: UUID,
    ): Book

}