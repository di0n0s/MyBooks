package com.example.books.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.books.data.BookRemoteMediator
import com.example.books.data.entity.BookListTuple
import com.example.books.data.room.BooksDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class BookListViewModel(
    private val remoteMediator: BookRemoteMediator,
    private val booksDao: BooksDao
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<BookListTuple>> by lazy { return@lazy getBookListFlow() }

    private fun getBookListFlow(): Flow<PagingData<BookListTuple>> {
        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = remoteMediator,
            pagingSourceFactory = { booksDao.getBookList() }
        )
        return pager.flow.cachedIn(viewModelScope)
    }
}
