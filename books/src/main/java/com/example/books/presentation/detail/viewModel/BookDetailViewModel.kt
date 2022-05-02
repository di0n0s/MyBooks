package com.example.books.presentation.detail.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.BooksRepository
import com.example.books.domain.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val repository: BooksRepository
) : ViewModel() {

    val userIntent = Channel<UserIntent>(Channel.UNLIMITED)

    private val _bookState = MutableStateFlow<GetBookState>(GetBookState.Idle)
    val bookState: StateFlow<GetBookState>
        get() = _bookState

    init {
        handleUserIntent()
    }

    private fun handleUserIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is UserIntent.GetBook -> getBook(it.id)
                }
            }
        }
    }


    private fun getBook(id: String) {
        viewModelScope.launch {
            _bookState.value = GetBookState.Loading

            _bookState.value = try {
                val book = repository.getBook(null, false, id)
                GetBookState.Success(book)
            } catch (e: Exception) {
                GetBookState.Error(e.localizedMessage)
            }
        }
    }

}

sealed class UserIntent {
    data class GetBook(val id: String) : UserIntent()
}

sealed class GetBookState {
    object Idle : GetBookState()
    object Loading : GetBookState()
    data class Success(val book: Book) : GetBookState()
    data class Error(val error: String?) : GetBookState()
}