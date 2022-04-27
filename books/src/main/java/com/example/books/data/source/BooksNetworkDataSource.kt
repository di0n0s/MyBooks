package com.example.books.data.source

import com.example.books.data.dto.BookDto
import com.example.books.data.dto.BookListDto
import com.example.books.data.service.MyBooksApiService
import com.example.books.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksNetworkDataSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val apiService: MyBooksApiService
) : BooksDataSource {
    override suspend fun getBookList(start: Int, loadSize: Int): List<BookListDto> =
        withContext(ioDispatcher) {
            apiService.getBookList(start = start, loadSize = loadSize)
        }

    override suspend fun getBook(id: String): BookDto = withContext(ioDispatcher) {
        apiService.getBook(id)
    }
}