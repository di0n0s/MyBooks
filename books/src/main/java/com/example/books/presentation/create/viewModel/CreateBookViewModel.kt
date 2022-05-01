package com.example.books.presentation.create.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.books.data.entity.BookEntity
import com.example.books.data.room.BooksDao
import com.example.books.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateBookViewModel @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val dao: BooksDao,
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
                val response = withContext(ioDispatcher) { dao.insertBook(book) }
                if (response >= 1) {
                    CreateBookState.Success
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
    object Success : CreateBookState()
    data class Error(val error: String?) : CreateBookState()
}

