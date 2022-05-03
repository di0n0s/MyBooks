package com.example.books.presentation.list.viewModel

import com.example.books.BookTestUtils
import com.example.books.BookTestUtils.exception
import com.example.books.MainCoroutineRule
import com.example.books.data.repository.BooksRepository
import com.example.books.domain.model.Book
import com.example.books.presentation.list.vo.BookPaginationVo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class BookListViewModelTest {

    private lateinit var viewModel: BookListViewModel

    private val book = Book.fromBookListDto(BookTestUtils.listDto)

    @Mock
    private lateinit var repository: BooksRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        viewModel = BookListViewModel(repository)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(repository)
    }

    @Test
    fun `GIVEN a list of book WHEN GetPagedBookList userIntent is sent THEN state change from Idle to Success`() =
        runBlockingTest {
            //GIVEN
            val loadSize = 20
            val list = listOf(book)
            val initialState = viewModel.bookListState.value
            Mockito.`when`(repository.getBookList(loadSize)).thenReturn(list)


            //WHEN
            viewModel.userIntent.send(UserIntent.GetPagedBookList(loadSize))

            //THEN
            Assert.assertEquals(initialState, GetPagedBookListState.Idle)

            val bookFromMap = list.map { book -> BookPaginationVo.BookVo.fromBook(book) }[0]
            val bookFromState =
                (viewModel.bookListState.value as GetPagedBookListState.Success).list[0] as BookPaginationVo.BookVo

            Assert.assertEquals(bookFromMap.id, bookFromState.id)
            Assert.assertEquals(bookFromMap.title, bookFromState.title)
            Assert.assertEquals(bookFromMap.imageUrl, bookFromState.imageUrl)

            Mockito.verify(repository).getBookList(loadSize)
        }

    @Test
    fun `GIVEN an exception WHEN GetPagedBookList userIntent is sent THEN state change from Idle to error`() =
        runBlockingTest {
            //GIVEN
            val loadSize = 20
            val initialState = viewModel.bookListState.value
            Mockito.`when`(repository.getBookList(loadSize)).thenThrow(exception)


            //WHEN
            viewModel.userIntent.send(UserIntent.GetPagedBookList(loadSize))

            //THEN
            Assert.assertEquals(initialState, GetPagedBookListState.Idle)

            Assert.assertEquals(
                viewModel.bookListState.value,
                GetPagedBookListState.Error(exception.localizedMessage)
            )

            Mockito.verify(repository).getBookList(loadSize)
        }

    @Test
    fun `GIVEN a book WHEN GetLastBookCreated userIntent is sent THEN state change from Idle to Success`() {
        runBlockingTest {
            //GIVEN
            val id = "id"
            val initialState = viewModel.bookListState.value
            Mockito.`when`(repository.getBookForList(id)).thenReturn(book)


            //WHEN
            viewModel.userIntent.send(UserIntent.GetLastBookCreated(id))

            //THEN
            Assert.assertEquals(initialState, GetPagedBookListState.Idle)

            val bookFromMap = BookPaginationVo.BookVo.fromBook(book)
            val bookFromState =
                (viewModel.bookListState.value as GetPagedBookListState.Success).list[0] as BookPaginationVo.BookVo

            Assert.assertEquals(bookFromMap.id, bookFromState.id)
            Assert.assertEquals(bookFromMap.title, bookFromState.title)
            Assert.assertEquals(bookFromMap.imageUrl, bookFromState.imageUrl)

            Mockito.verify(repository).getBookForList(id)
        }
    }

    @Test
    fun `GIVEN an exception WHEN GetLastBookCreated userIntent is sent THEN state change from Idle to Error`() {
        runBlockingTest {
            //GIVEN
            val id = "id"
            val initialState = viewModel.bookListState.value
            Mockito.`when`(repository.getBookForList(id)).thenThrow(exception)

            //WHEN
            viewModel.userIntent.send(UserIntent.GetLastBookCreated(id))

            //THEN
            Assert.assertEquals(initialState, GetPagedBookListState.Idle)

            Assert.assertEquals(
                viewModel.bookListState.value,
                GetPagedBookListState.Error(exception.localizedMessage)
            )

            Mockito.verify(repository).getBookForList(id)
        }
    }
}