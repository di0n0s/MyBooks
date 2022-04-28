package com.example.books.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    val lastBookId: Int,
    val remainingBooks: Int
) {
    @PrimaryKey(autoGenerate = true)
    var databaseId: Int = 0

}