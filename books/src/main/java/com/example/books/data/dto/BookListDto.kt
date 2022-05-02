package com.example.books.data.dto

import com.google.gson.annotations.SerializedName

data class BookListDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("image") val imageUrl: String
)