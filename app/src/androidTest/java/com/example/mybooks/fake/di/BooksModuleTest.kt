package com.example.mybooks.fake.di

import com.example.books.data.db.source.BooksRoomDataSource
import com.example.books.data.network.service.MyBooksApiService
import com.example.books.data.network.source.BooksNetworkDataSource
import com.example.books.data.source.BooksDataSource
import com.example.books.di.BooksModule
import com.example.books.di.NetworkDataSource
import com.example.books.di.RoomDataSource
import com.example.mybooks.fake.FakeMyBooksApiService
import dagger.Binds
import dagger.Module
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [BooksModule::class]
)
abstract class BooksModuleTest {

    @Binds
    @NetworkDataSource
    abstract fun bindBooksNetworkDataSource(booksNetworkDataSource: BooksNetworkDataSource): BooksDataSource

    @Binds
    @RoomDataSource
    abstract fun bindBooksRoomDataSource(booksRoomDataSource: BooksRoomDataSource): BooksDataSource

    @Binds
    abstract fun bindFakeMyBooksApiService(fakeBooksApiService: FakeMyBooksApiService): MyBooksApiService
}