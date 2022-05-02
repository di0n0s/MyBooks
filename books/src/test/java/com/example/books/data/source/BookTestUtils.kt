package com.example.books.data.source

import com.example.books.data.entity.BookEntity

object BookTestUtils {

    val entity = BookEntity(
        id = "id",
        title = "title",
        imageUrl = "imageUrl",
        author = "author",
        price = 2.01
    )
}