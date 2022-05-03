package com.example.books

import com.example.books.data.dto.BookDetailDto
import com.example.books.data.dto.BookListDto
import com.example.books.data.entity.BookEntity

object BookTestUtils {

    val entity = BookEntity(
        id = "id",
        title = "title",
        imageUrl = "imageUrl",
        author = "author",
        price = 2.01
    )

    val listDto = BookListDto(
        id = "id",
        title = "title",
        imageUrl = "imageUrl"
    )

    val detailDto = BookDetailDto(
        id = "id",
        title = "title",
        imageUrl = "imageUrl",
        author = "author",
        price = 2.00
    )

    val exception = RuntimeException()


}