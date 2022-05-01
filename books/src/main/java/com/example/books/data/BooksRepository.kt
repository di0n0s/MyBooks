package com.example.books.data

import com.example.books.data.source.BooksDataSource
import com.example.books.domain.model.Book
import java.util.*
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

    suspend fun getBook(dataSource: DataSource?, isForList: Boolean, id: UUID): Book {
        return if (dataSource != null) {
            when (dataSource) {
                DataSource.ROOM -> roomDataSource.getBook(id, isForList)!!
                DataSource.NETWORK -> networkDataSource.getBook(id, isForList)!!
            }
        } else {
            return roomDataSource.getBook(id, isForList) ?: networkDataSource.getBook(
                id,
                isForList
            )!!
        }
    }
}

sealed class DataSource {
    object ROOM : DataSource()
    object NETWORK : DataSource()
}