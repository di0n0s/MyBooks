package com.example.books.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.books.data.db.room.AppDatabase
import com.example.books.data.db.room.BooksDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataBaseModule {

    @Provides
    fun provideChannelDao(roomDatabase: RoomDatabase): BooksDao {
        return (roomDatabase as AppDatabase).booksDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RoomDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase::class.java.name
        ).build()
    }
}