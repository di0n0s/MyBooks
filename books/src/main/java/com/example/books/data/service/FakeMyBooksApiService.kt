package com.example.books.data.service

import com.example.books.data.dto.BookDto
import com.example.books.data.dto.BookListDto
import com.example.books.data.dto.BookListResponse
import javax.inject.Inject

class FakeMyBooksApiService @Inject constructor() : MyBooksApiService {

    private val list = mutableListOf<BookListDto>()

    init {
        for (i in 1 until 1000) {
            list.add(BookListDto(id = i, title = "Book $i"))
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


        val subList = list.subList(startNumber, startNumber + loadSizeNumber)

        val remainingBooks = list.size - subList.last().id

        return BookListResponse(subList, remainingBooks)
    }


    override suspend fun getBook(id: String): BookDto {
        TODO("Not yet implemented")
    }
}