package com.example.books.presentation.detail.vo

import android.content.res.Resources
import com.example.books.domain.model.Book
import java.text.DecimalFormat

class BookDetailVo private constructor(
    val id: String,
    val title: String,
    val imageUrl: String?,
    val author: String,
    val price: String
) {

    companion object {
        fun fromBook(book: Book): BookDetailVo {
            val widthPixels = Resources.getSystem().displayMetrics.widthPixels
            return BookDetailVo(
                id = book.id,
                title = book.title,
                author = book.author.toString(),
                price = """${book.price?.formatPrice()} €""",
                imageUrl = """${book.imageUrl}$widthPixels"""
            )
        }

        private fun Double.formatPrice(): String {
            val format = DecimalFormat("0.#")
            return format.format(this)
        }
    }
}