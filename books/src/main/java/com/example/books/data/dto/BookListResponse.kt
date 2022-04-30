package com.example.books.data.dto

data class BookListResponse(
    val data: List<BookListDto>,
    val remainingBooks: Int
)