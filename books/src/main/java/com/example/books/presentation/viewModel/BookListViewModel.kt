package com.example.books.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.source.BooksDataSource
import com.example.books.presentation.BookPaginationVo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class BookListViewModel @Inject constructor(
    @Named("BooksNetworkDataSource") private val booksNetworkDataSource: BooksDataSource
) : ViewModel() {

    val userIntent = Channel<UserIntent>(Channel.UNLIMITED)

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
                    is UserIntent.GetPagedBookList -> getPagedBookList(it.start, it.loadSize)
                }
            }
        }
    }

    private fun getPagedBookList(start: Int?, loadSize: Int) {
        viewModelScope.launch {
            _bookListState.value = GetPagedBookListState.Loading

            _bookListState.value = try {
                val response = booksNetworkDataSource.getBookList(start ?: 0, loadSize).map { dto ->
                    BookPaginationVo.BookVo(
                        id = dto.id.toString(),
                        title = dto.title
                    )
                }
                GetPagedBookListState.Success(response)
            } catch (e: Exception) {
                GetPagedBookListState.Error(e.localizedMessage)
            }
        }
    }

}

sealed class UserIntent {
    data class GetPagedBookList(val start: Int?, val loadSize: Int) : UserIntent()
}

sealed class GetPagedBookListState {
    object Idle : GetPagedBookListState()
    object Loading : GetPagedBookListState()
    data class Success(val list: List<BookPaginationVo>) : GetPagedBookListState()
    data class Error(val error: String?) : GetPagedBookListState()
}
