package com.example.books.data.service

import com.example.books.data.dto.BookDto
import com.example.books.data.dto.BookListDto
import javax.inject.Inject

class FakeMyBooksApiService @Inject constructor() : MyBooksApiService {

    private val list = mutableListOf<BookListDto>()

    override suspend fun getBookList(start: Int?, loadSize: Int?): List<BookListDto> {
        var startNumber = 1
        var loadSizeNumber = 20

        if (start != null) {
            startNumber = start
        }

        if (loadSize != null) {
            loadSizeNumber = loadSize
        }

        for (i in startNumber until startNumber + loadSizeNumber) {
            list.add(BookListDto(id = i, title = "Book $i"))
        }

        return list
    }


    override suspend fun getBook(id: String): BookDto {
        TODO("Not yet implemented")
    }
}