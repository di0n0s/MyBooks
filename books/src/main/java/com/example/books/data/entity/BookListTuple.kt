package com.example.books.data.entity

import androidx.room.ColumnInfo

data class BookListTuple(
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "title") val title: String
)