package com.example.books.data.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class BookDetailDto(
    @SerializedName("id") val id: UUID,
    @SerializedName("title") val title: String,
    @SerializedName("image") val imageUrl: String,
    @SerializedName("author") val author: String,
    @SerializedName("price") val price: Double
)