package com.example.books.presentation

sealed class BookPaginationVo {
    data class BookVo(
        val id: String,
        val title: String
    ) : BookPaginationVo()

    object Loading : BookPaginationVo()
}