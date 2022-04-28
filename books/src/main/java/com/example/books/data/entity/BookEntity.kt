package com.example.books.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity(
    val id: Int,
    val title: String,
    val imageUrl: String? = null,
    val author: String? = null,
    val price: Double? = null
) {
    @PrimaryKey(autoGenerate = true)
    var databaseId: Int = 0

}