package com.example.books.data.repository

import android.util.Log
import com.example.books.data.source.BooksDataSource
import com.example.books.di.NetworkDataSource
import com.example.books.di.RoomDataSource
import com.example.books.domain.model.Book
import javax.inject.Inject

class BooksRepository @Inject constructor(
    @NetworkDataSource private val networkDataSource: BooksDataSource,
    @RoomDataSource private val roomDataSource: BooksDataSource,
) {

    suspend fun getBookList(loadSize: Int): List<Book> {
        val list = arrayListOf<Book>()
        var dbListSize = 0
        var exception: Exception? = null
        try {
            val dbList = roomDataSource.getBookList(loadSize)
            dbListSize = dbList.size
            list.addAll(dbList)
        } catch (e: Exception) {
            exception = e
        }

        try {
            val apiList = networkDataSource.getBookList(loadSize - dbListSize)
            list.addAll(apiList)
        } catch (e: Exception) {
            exception = e
        }

        if (list.isEmpty() && exception != null) {
            throw exception
        } else {
            return list
        }

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