package com.example.mybooks.fake.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.books.data.room.BooksDao
import com.example.books.di.DataBaseModule
import com.example.mybooks.fake.FakeAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DataBaseModule::class]
)
class DataBaseModuleTest {

    @Provides
    fun provideChannelDao(roomDatabase: RoomDatabase): BooksDao {
        return (roomDatabase as FakeAppDatabase).booksDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): RoomDatabase {
        return Room.databaseBuilder(
            appContext,
            FakeAppDatabase::class.java,
            FakeAppDatabase::class.java.name
        ).build()
    }
}