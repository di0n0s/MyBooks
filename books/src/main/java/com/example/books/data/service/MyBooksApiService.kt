package com.example.books.data.service

import com.example.books.data.dto.BookDetailDto
import com.example.books.data.dto.BookListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MyBooksApiService {

    @GET("/api/v1/items")
    suspend fun getBookList(
        @Query("offset") start: Int?,
        @Query("count") loadSize: Int? = null
    ): BookListResponse

    @GET("/api/v1/items/{id}")
    suspend fun getBook(
        @Path("id") id: String,
    ): BookDetailDto
}