package com.example.books.presentation.create.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.db.entity.BookEntity
import com.example.books.data.source.BooksDataSource
import com.example.books.di.RoomDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBookViewModel @Inject constructor(
    @RoomDataSource private val roomDataSource: BooksDataSource,
    application: Application
) : AndroidViewModel(application) {

    val userIntent = Channel<UserIntent>(Channel.UNLIMITED)

    private val _createBookState =
        MutableStateFlow<CreateBookState>(CreateBookState.Idle)
    val createBookState: StateFlow<CreateBookState>
        get() = _createBookState

    init {
        handleUserIntent()
    }

    private fun handleUserIntent() {
        viewModelScope.launch {
            userIntent.consumeAsFlow().collect {
                when (it) {
                    is UserIntent.CreateBook -> createBook(it.book)
                }
            }
        }
    }

    private fun createBook(book: BookEntity) {
        viewModelScope.launch {
            _createBookState.value = CreateBookState.Loading

            _createBookState.value = try {
                val isInserted = roomDataSource.insertBook(book)
                if (isInserted) {
                    CreateBookState.Success(book.id)
                } else {
                    CreateBookState.Error((getApplication() as Context).getString(com.example.core.R.string.common_error))
                }
            } catch (e: Exception) {
                CreateBookState.Error(e.localizedMessage)
            }
        }
    }

}

sealed class UserIntent {
    data class CreateBook(val book: BookEntity) : UserIntent()
}

sealed class CreateBookState {
    object Idle : CreateBookState()
    object Loading : CreateBookState()
    data class Success(val bookId: String) : CreateBookState()
    data class Error(val error: String?) : CreateBookState()
}

