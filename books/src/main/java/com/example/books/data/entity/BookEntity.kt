package com.example.books.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val id: UUID,
    val title: String,
    val imageUrl: String? = null,
    val author: String,
    val price: Double
)