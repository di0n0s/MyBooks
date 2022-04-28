package com.example.books.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.books.data.BookRemoteMediator
import com.example.books.data.entity.BookEntity
import com.example.books.data.room.AppDatabase
import com.example.books.data.service.MyBooksApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class BookListViewModel @Inject constructor(
    private val db: AppDatabase,
    private val apiService: MyBooksApiService
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<BookEntity>> by lazy { return@lazy getBookListFlow() }

    private fun getBookListFlow(): Flow<PagingData<BookEntity>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 100),
            remoteMediator = BookRemoteMediator(db, apiService)
        ) {
            db.booksDao().getPagedBookList()
        }

        return pager.flow
    }
}
