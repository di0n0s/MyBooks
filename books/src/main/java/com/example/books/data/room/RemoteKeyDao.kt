package com.example.books.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.books.data.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(key: RemoteKeyEntity)

    @Query("SELECT * FROM remote_keys ORDER by lastBookId DESC")
    suspend fun getRemoteKeyList(): List<RemoteKeyEntity>

    @Query("DELETE FROM remote_keys")
    suspend fun clearAll()
}