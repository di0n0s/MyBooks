package com.example.books.data.source

import com.example.books.data.room.BooksDao
import com.example.books.di.IoDispatcher
import com.example.books.domain.model.Book
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksRoomDataSource @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val dao: BooksDao
) : BooksDataSource {

    override suspend fun getBookList(start: Int, loadSize: Int): List<Book> =
        withContext(ioDispatcher) {
            dao.getBookList().map { dto -> Book.fromBookEntity(dto) }
        }

    override suspend fun getBook(id: String): Book = withContext(ioDispatcher) {
        val entity = dao.getBook(id)
        return@withContext Book.fromBookEntity(entity)
    }
}