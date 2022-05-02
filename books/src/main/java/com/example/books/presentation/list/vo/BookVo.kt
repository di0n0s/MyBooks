package com.example.books.presentation.list.vo

import android.content.res.Resources
import com.example.books.domain.model.Book
import java.util.*

sealed class BookPaginationVo {
    class BookVo private constructor(
        val id: UUID,
        val title: String,
        val imageUrl: String?,
    ) : BookPaginationVo() {

        companion object {
            fun fromBook(book: Book): BookVo {
                val widthPixels = Resources.getSystem().displayMetrics.widthPixels / 3
                return BookVo(
                    id = book.id,
                    imageUrl = """${book.imageUrl}$widthPixels""",
                    title = book.title
                )
            }
        }


    }


    object Loading : BookPaginationVo()
}