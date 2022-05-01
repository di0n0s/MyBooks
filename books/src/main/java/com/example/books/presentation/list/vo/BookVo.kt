package com.example.books.presentation.list.vo

sealed class BookPaginationVo {
    data class BookVo(
        val id: String,
        val title: String
    ) : BookPaginationVo()

    object Loading : BookPaginationVo()
}