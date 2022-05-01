package com.example.books.data.service

import com.example.books.data.dto.BookDetailDto
import com.example.books.data.dto.BookListDto
import com.example.books.data.dto.BookListResponse
import java.util.*
import javax.inject.Inject

class FakeMyBooksApiService @Inject constructor() : MyBooksApiService {

    private val list = mutableListOf<BookListDto>()

    init {
        for (i in 1 until 1000) {
            list.add(BookListDto(id = UUID.randomUUID(), title = "Book $i"))
        }
    }

    override suspend fun getBookList(start: Int?, loadSize: Int?): BookListResponse {
        var loadSizeNumber = 100
        var startNumber = 0

        if (start != null) {
            startNumber = start - 1
        }

        if (loadSize != null) {
            loadSizeNumber = loadSize

            if (startNumber + loadSizeNumber > list.size) {
                loadSizeNumber = list.size - startNumber
            }
        }

        val toIndex = startNumber + loadSizeNumber

        val subList = list.subList(startNumber, toIndex)

        val remainingBooks = list.size - toIndex

        return BookListResponse(subList, remainingBooks)
    }


    override suspend fun getBook(id: String): BookDetailDto {
        TODO("Not yet implemented")
    }
}