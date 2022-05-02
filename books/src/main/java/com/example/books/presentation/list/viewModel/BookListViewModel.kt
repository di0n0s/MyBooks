package com.example.books.presentation.list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.BooksRepository
import com.example.books.data.DataSource
import com.example.books.presentation.list.vo.BookPaginationVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(
    private val repository: BooksRepository
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
                    repository.getBookList(loadSize).map { book ->
                        BookPaginationVo.BookVo.fromBook(book)
                    }
                GetPagedBookListState.Success(response)
            } catch (e: Exception) {
                GetPagedBookListState.Error(e.localizedMessage)
            }
        }
    }

    private fun getLastBookCreated(id: String) {
        viewModelScope.launch {
            _bookListState.value = GetPagedBookListState.Loading

            _bookListState.value = try {
                val book = repository.getBook(DataSource.ROOM, true, id)
                val bookVo =
                    BookPaginationVo.BookVo.fromBook(book)
                GetPagedBookListState.Success(listOf(bookVo))
            } catch (e: Exception) {
                GetPagedBookListState.Error(e.localizedMessage)
            }
        }
    }

}

sealed class UserIntent {
    data class GetPagedBookList(val loadSize: Int) : UserIntent()
    data class GetLastBookCreated(val id: String) : UserIntent()
}

sealed class GetPagedBookListState {
    object Idle : GetPagedBookListState()
    object Loading : GetPagedBookListState()
    data class Success(val list: List<BookPaginationVo>) : GetPagedBookListState()
    data class Error(val error: String?) : GetPagedBookListState()
}
