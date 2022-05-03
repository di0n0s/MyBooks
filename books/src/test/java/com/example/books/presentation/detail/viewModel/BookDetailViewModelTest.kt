package com.example.books.presentation.detail.viewModel

import com.example.books.BookTestUtils.detailDto
import com.example.books.BookTestUtils.exception
import com.example.books.MainCoroutineRule
import com.example.books.data.repository.BooksRepository
import com.example.books.domain.model.Book
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BookDetailViewModelTest {

    private lateinit var viewModel: BookDetailViewModel

    private val book = Book.fromBookDetailDto(detailDto)

    @Mock
    private lateinit var repository: BooksRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        viewModel = BookDetailViewModel(repository)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(repository)
    }

    @Test
    fun `GIVEN a book WHEN GetBook userIntent is sent THEN state change from Idle to Success`() =
        runBlockingTest {
            //GIVEN
            val id = "id"
            val initialState = viewModel.bookState.value
            Mockito.`when`(repository.getBook(id)).thenReturn(book)

            //WHEN
            viewModel.userIntent.send(UserIntent.GetBook(id))

            //THEN
            Assert.assertEquals(initialState, GetBookState.Idle)
            Assert.assertEquals(viewModel.bookState.value, GetBookState.Success(book))

            Mockito.verify(repository).getBook(id)
        }

    @Test
    fun `GIVEN an exception WHEN GetBook userIntent is sent THEN state change from Idle to Error`() =
        runBlockingTest {
            //GIVEN
            val id = "id"
            Mockito.`when`(repository.getBook(id)).thenThrow(exception)
            val initialState = viewModel.bookState.value

            //WHEN
            viewModel.userIntent.send(UserIntent.GetBook(id))

            //THEN
            Assert.assertEquals(initialState, GetBookState.Idle)
            Assert.assertEquals(
                viewModel.bookState.value,
                GetBookState.Error(exception.localizedMessage)
            )

            Mockito.verify(repository).getBook(id)
        }
}