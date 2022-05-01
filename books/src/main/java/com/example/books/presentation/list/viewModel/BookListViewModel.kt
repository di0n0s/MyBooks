package com.example.books.presentation.list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.room.BooksDao
import com.example.books.data.source.BooksDataSource
import com.example.books.presentation.list.vo.BookPaginationVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BookListViewModel @Inject constructor(
    @Named("BooksNetworkDataSource") private val booksNetworkDataSource: BooksDataSource,
    private val dao: BooksDao
) : ViewModel() {

    val userIntent = Channel<UserIntent>(Channel.UNLIMITED)

    val bookListVo = arrayListOf<BookPaginationVo>()

    private val _bookListState = MutableStateFlow<GetPagedBookListState>(GetPagedBookListState.Idle)
    val bookListState: StateFlow<GetPagedBookListState>
        get() = _bookListState

    init {
        handleUserIntent()
    }

    private fun handleUserIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is UserIntent.GetPagedBookList -> getPagedBookList(it.loadSize)
                    is UserIntent.GetLastBookCreated -> getLastBookCreated(it.id)
                }
            }
        }
    }

    private fun getPagedBookList(loadSize: Int) {
        viewModelScope.launch {
            _bookListState.value = GetPagedBookListState.Loading

            _bookListState.value = try {
                val response =
                    booksNetworkDataSource.getBookList(loadSize).map { book ->
                        BookPaginationVo.BookVo(
                            id = book.id.toString(),
                            title = book.title
                        )
                    }
                GetPagedBookListState.Success(response)
            } catch (e: Exception) {
                GetPagedBookListState.Error(e.localizedMessage)
            }
        }
    }

    private fun getLastBookCreated(id: UUID) {
        viewModelScope.launch {
            _bookListState.value = GetPagedBookListState.Loading

            _bookListState.value = try {
                val book = withContext(Dispatchers.IO) { dao.getBook(id) }
                val bookVo = BookPaginationVo.BookVo(id = book.id.toString(), title = book.title)
                GetPagedBookListState.Success(listOf(bookVo))
            } catch (e: Exception) {
                GetPagedBookListState.Error(e.localizedMessage)
            }
        }
    }

}

sealed class UserIntent {
    data class GetPagedBookList(val loadSize: Int) : UserIntent()
    data class GetLastBookCreated(val id: UUID) : UserIntent()
}

sealed class GetPagedBookListState {
    object Idle : GetPagedBookListState()
    object Loading : GetPagedBookListState()
    data class Success(val list: List<BookPaginationVo>) : GetPagedBookListState()
    data class Error(val error: String?) : GetPagedBookListState()
}
