package com.example.books.data.dto

import com.google.gson.annotations.SerializedName

data class BookDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("author") val author: String,
    @SerializedName("price") val price: Double
)