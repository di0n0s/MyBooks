package com.example.books.data.source

import com.example.books.data.entity.BookEntity
import com.example.books.data.service.MyBooksApiService
import com.example.books.di.IoDispatcher
import com.example.books.domain.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject

class BooksNetworkDataSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val apiService: MyBooksApiService
) : BooksDataSource {

    private var start = 1

    override suspend fun getBookList(loadSize: Int): List<Book> = withContext(ioDispatcher) {
        val list = apiService.getBookList(
            start = start,
            loadSize = loadSize
        )
        start += list.size
        return@withContext list.map { dto -> Book.fromBookListDto(dto) }
    }

    override suspend fun getBook(id: UUID, isForList: Boolean): Book = withContext(ioDispatcher) {
        val dto = apiService.getBook(id)
        return@withContext Book.fromBookDetailDto(dto)
    }

    override suspend fun insertBook(book: BookEntity): Boolean {
        TODO("Not yet implemented")
    }
}