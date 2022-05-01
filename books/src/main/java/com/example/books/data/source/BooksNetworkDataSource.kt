package com.example.books.data.source

import com.example.books.data.service.MyBooksApiService
import com.example.books.di.IoDispatcher
import com.example.books.domain.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksNetworkDataSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val apiService: MyBooksApiService
) : BooksDataSource {

    override suspend fun getBookList(start: Int, loadSize: Int): List<Book> =
        withContext(ioDispatcher) {
            apiService.getBookList(
                start = start,
                loadSize = loadSize
            ).data.map { dto -> Book.fromBookListDto(dto) }
        }

    override suspend fun getBook(id: String): Book = withContext(ioDispatcher) {
        val dto = apiService.getBook(id)
        return@withContext Book.fromBookDetailDto(dto)
    }
}