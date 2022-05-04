package com.example.books.data.network.dto

import com.google.gson.annotations.SerializedName

data class BookListDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("image") val imageUrl: String
)