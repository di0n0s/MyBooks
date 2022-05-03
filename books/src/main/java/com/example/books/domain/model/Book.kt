package com.example.books.domain.model

import com.example.books.data.dto.BookDetailDto
import com.example.books.data.dto.BookListDto
import com.example.books.data.entity.BookEntity

class Book private constructor(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val author: String?,
    val price: Double?
) {
    companion object {
        fun fromBookListDto(dto: BookListDto): Book =
            Book(
                id = dto.id,
                title = dto.title,
                author = null,
                price = null,
                imageUrl = dto.imageUrl,
            )

        fun fromBookDetailDto(dto: BookDetailDto): Book =
            Book(
                id = dto.id,
                title = dto.title,
                author = dto.author,
                price = dto.price,
                imageUrl = dto.imageUrl
            )


        fun fromBookEntity(entity: BookEntity): Book =
            Book(
                id = entity.id,
                title = entity.title,
                author = entity.author,
                price = entity.price,
                imageUrl = entity.imageUrl
            )

    }
}