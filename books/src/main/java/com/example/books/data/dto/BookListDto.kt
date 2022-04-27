package com.example.books.data.dto

import com.google.gson.annotations.SerializedName

data class BookListDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
)