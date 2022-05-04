package com.example.books.di

import com.example.books.data.service.FakeMyBooksApiService
import com.example.books.data.service.MyBooksApiService
import com.example.books.data.source.BooksDataSource
import com.example.books.data.source.BooksNetworkDataSource
import com.example.books.data.source.BooksRoomDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
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

//        @Provides
//        fun provideMyBooksApiService(
//            retrofitBuilder: Retrofit.Builder,
//        ): MyBooksApiService {
//
//            return retrofitBuilder
//                .build()
//                .create(MyBooksApiService::class.java)
//        }

    }

    @Binds
    @NetworkDataSource
    abstract fun bindBooksNetworkDataSource(booksNetworkDataSource: BooksNetworkDataSource): BooksDataSource

    @Binds
    @RoomDataSource
    abstract fun bindBooksRoomDataSource(booksRoomDataSource: BooksRoomDataSource): BooksDataSource

    @Binds
    abstract fun bindFakeMyBooksApiService(fakeBooksApiService: FakeMyBooksApiService): MyBooksApiService

}