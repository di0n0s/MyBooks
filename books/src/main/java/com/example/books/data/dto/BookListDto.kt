package com.example.books.data.dto

import com.google.gson.annotations.SerializedName
import java.util.*

data class BookListDto(
    @SerializedName("id") val id: UUID,
    @SerializedName("title") val title: String,
)