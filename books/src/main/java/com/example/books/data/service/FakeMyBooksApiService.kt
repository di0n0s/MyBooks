package com.example.books.data.service

import com.example.books.data.dto.BookDetailDto
import com.example.books.data.dto.BookListDto
import java.util.*
import javax.inject.Inject

class FakeMyBooksApiService @Inject constructor() : MyBooksApiService {

    private val list = mutableListOf<BookListDto>()

    init {
        for (i in 1 until 1000) {
            list.add(BookListDto(id = UUID.randomUUID(), title = "Book $i"))
        }
    }

    override suspend fun getBookList(start: Int?, loadSize: Int?): List<BookListDto> {
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

        return list.subList(startNumber, toIndex)
    }


    override suspend fun getBook(id: UUID): BookDetailDto {
        TODO("Not yet implemented")
    }
}