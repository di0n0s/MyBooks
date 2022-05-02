package com.example.books.data

import com.example.books.data.source.BooksDataSource
import com.example.books.domain.model.Book
import javax.inject.Inject
import javax.inject.Named

class BooksRepository @Inject constructor(
    @Named("BooksNetworkDataSource") private val networkDataSource: BooksDataSource,
    @Named("BooksRoomDataSource") private val roomDataSource: BooksDataSource,
) {

    suspend fun getBookList(loadSize: Int): List<Book> {
        val list = arrayListOf<Book>()
        val dbList = roomDataSource.getBookList(loadSize)
        list.addAll(dbList)
        val apiList = networkDataSource.getBookList(loadSize - dbList.size)
        list.addAll(apiList)
        return list
    }

    suspend fun getBook(id: String): Book {
        return try {
            roomDataSource.getBook(id)
        } catch (e: Exception) {
            networkDataSource.getBook(id)
        }
    }

    suspend fun getBookForList(id: String): Book = roomDataSource.getBookForList(id)
}