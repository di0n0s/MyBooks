package com.example.books.presentation.create.viewModel

import android.app.Application
import android.content.Context
import com.example.books.BookTestUtils.entity
import com.example.books.BookTestUtils.exception
import com.example.books.MainCoroutineRule
import com.example.books.data.source.BooksDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class CreateBookViewModelTest {

    private lateinit var viewModel: CreateBookViewModel

    @Mock
    private lateinit var roomDataSource: BooksDataSource

    @Mock
    private lateinit var application: Application

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        viewModel = CreateBookViewModel(roomDataSource, application)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(roomDataSource, application)
    }

    @Test
    fun `GIVEN true WHEN CreateBook userIntent is sent THEN state change from Idle to Success`() =
        runBlockingTest {
            //GIVEN
            val initialState = viewModel.createBookState.value
            Mockito.`when`(roomDataSource.insertBook(entity)).thenReturn(true)

            //WHEN
            viewModel.userIntent.send(UserIntent.CreateBook(entity))

            //THEN
            Assert.assertEquals(initialState, CreateBookState.Idle)
            Assert.assertEquals(viewModel.createBookState.value, CreateBookState.Success(entity.id))

            Mockito.verify(roomDataSource).insertBook(entity)
        }

    @Test
    fun `GIVEN false WHEN CreateBook userIntent is sent THEN state change from Idle to Error`() =
        runBlockingTest {
            //GIVEN
            val initialState = viewModel.createBookState.value
            Mockito.`when`(roomDataSource.insertBook(entity)).thenReturn(false)
            val errorText = "There was an error"
            Mockito.`when`((application as Context).getString(com.example.core.R.string.common_error))
                .thenReturn(errorText)

            //WHEN
            viewModel.userIntent.send(UserIntent.CreateBook(entity))

            //THEN
            Assert.assertEquals(initialState, CreateBookState.Idle)
            Assert.assertEquals(viewModel.createBookState.value, CreateBookState.Error(errorText))

            Mockito.verify(roomDataSource).insertBook(entity)
            Mockito.verify((application as Context))
                .getString(com.example.core.R.string.common_error)
        }

    @Test
    fun `GIVEN an exception WHEN CreateBook userIntent is sent THEN state change from Idle to Error`() =
        runBlockingTest {
            //GIVEN
            val initialState = viewModel.createBookState.value
            Mockito.`when`(roomDataSource.insertBook(entity)).thenThrow(exception)

            //WHEN
            viewModel.userIntent.send(UserIntent.CreateBook(entity))

            //THEN
            Assert.assertEquals(initialState, CreateBookState.Idle)
            Assert.assertEquals(
                viewModel.createBookState.value,
                CreateBookState.Error(exception.localizedMessage)
            )

            Mockito.verify(roomDataSource).insertBook(entity)
        }
}