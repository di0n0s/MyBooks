package com.example.books.data.source

import com.example.books.data.entity.BookEntity
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

    private var start = 1

    override suspend fun getBookList(loadSize: Int): List<Book> = withContext(ioDispatcher) {
        val list = dao.getBookList(start, loadSize)
        start += list.size
        return@withContext list.map { dto -> Book.fromBookEntity(dto) }
    }

    override suspend fun getBook(id: String): Book = withContext(ioDispatcher) {
        val entity = dao.getBook(id)
        return@withContext Book.fromBookEntity(entity)
    }

    override suspend fun getBookForList(id: String): Book = withContext(ioDispatcher) {
        val entity = dao.getBook(id)
        start += 1
        return@withContext Book.fromBookEntity(entity)
    }

    override suspend fun insertBook(book: BookEntity): Boolean = withContext(ioDispatcher) {
        return@withContext dao.insertBook(book) >= 1
    }
}