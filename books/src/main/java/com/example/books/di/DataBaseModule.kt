package com.example.books.di

import android.content.Context
import androidx.room.Room
import com.example.books.data.room.AppDatabase
import com.example.books.data.room.BooksDao
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
    fun provideChannelDao(appDatabase: AppDatabase): BooksDao {
        return appDatabase.booksDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase::class.java.name
        ).build()
    }
}