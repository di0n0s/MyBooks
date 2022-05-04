package com.example.books.di

import com.example.books.data.db.source.BooksRoomDataSource
import com.example.books.data.network.service.MyBooksApiService
import com.example.books.data.network.source.BooksNetworkDataSource
import com.example.books.data.source.BooksDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NetworkDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RoomDataSource

@Module
@InstallIn(ViewModelComponent::class)
abstract class BooksModule {

    companion object {
        @Provides
        fun provideMyBooksApiService(
            retrofitBuilder: Retrofit.Builder,
        ): MyBooksApiService {

            return retrofitBuilder
                .build()
                .create(MyBooksApiService::class.java)
        }
    }

    @Binds
    @NetworkDataSource
    abstract fun bindBooksNetworkDataSource(booksNetworkDataSource: BooksNetworkDataSource): BooksDataSource

    @Binds
    @RoomDataSource
    abstract fun bindBooksRoomDataSource(booksRoomDataSource: BooksRoomDataSource): BooksDataSource
}