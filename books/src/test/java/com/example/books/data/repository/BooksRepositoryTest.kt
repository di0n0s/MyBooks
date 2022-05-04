package com.example.books.data.repository

import com.example.books.BookTestUtils.exception
import com.example.books.BookTestUtils.listDto
import com.example.books.MainCoroutineRule
import com.example.books.data.source.BooksDataSource
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
class BooksRepositoryTest {

    private lateinit var repository: BooksRepository

    private val book = Book.fromBookListDto(listDto)

    @Mock
    private lateinit var networkDataSource: BooksDataSource

    @Mock
    private lateinit var roomDataSource: BooksDataSource

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setUp() {
        repository = BooksRepository(networkDataSource, roomDataSource)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(networkDataSource, roomDataSource)
    }

    @Test
    fun `GIVEN a list from Room WHEN getBookList is called THEN loadSize for Network is loadSize minus listSize from Room and result is the sum of both list`() =
        runBlockingTest {
            //GIVEN
            val loadSize = 20
            val list = listOf(book)
            Mockito.`when`(roomDataSource.getBookList(loadSize)).thenReturn(list)
            Mockito.`when`(networkDataSource.getBookList(loadSize - list.size)).thenReturn(list)

            //WHEN
            val result = repository.getBookList(loadSize)

            //THEN
            Assert.assertEquals(2, result.size)

            Mockito.verify(roomDataSource).getBookList(loadSize)
            Mockito.verify(networkDataSource).getBookList(loadSize - list.size)
        }

    @Test
    fun `GIVEN an exception from Room and list from Network WHEN getBookList is called THEN result is list from network`() =
        runBlockingTest {
            //GIVEN
            val loadSize = 20
            val list = listOf(book)
            Mockito.`when`(roomDataSource.getBookList(loadSize)).thenThrow(exception)
            Mockito.`when`(networkDataSource.getBookList(loadSize)).thenReturn(list)

            //WHEN
            val result = repository.getBookList(loadSize)

            //THEN
            Assert.assertEquals(1, result.size)

            Mockito.verify(roomDataSource).getBookList(loadSize)
            Mockito.verify(networkDataSource).getBookList(loadSize)
        }

    @Test
    fun `GIVEN a list from Room and exception from Network WHEN getBookList is called THEN result is list from room`() =
        runBlockingTest {
            //GIVEN
            val loadSize = 20
            val list = listOf(book)
            Mockito.`when`(roomDataSource.getBookList(loadSize)).thenReturn(list)
            Mockito.`when`(networkDataSource.getBookList(loadSize - list.size)).thenThrow(exception)

            //WHEN
            val result = repository.getBookList(loadSize)

            //THEN
            Assert.assertEquals(1, result.size)

            Mockito.verify(roomDataSource).getBookList(loadSize)
            Mockito.verify(networkDataSource).getBookList(loadSize - list.size)
        }

    @Test
    fun `GIVEN exceptions from Room and Network WHEN getBookList is called THEN result is exception from network`() =
        runBlockingTest {
            //GIVEN
            val loadSize = 20
            val exceptionFromRoom = RuntimeException("Exception from Room")
            val exceptionFromNetwork = RuntimeException("Exception from Network")
            Mockito.`when`(roomDataSource.getBookList(loadSize)).thenThrow(exceptionFromRoom)
            Mockito.`when`(networkDataSource.getBookList(loadSize)).thenThrow(exceptionFromNetwork)

            //WHEN
            var result: RuntimeException? = null
            try {
                repository.getBookList(loadSize)
            } catch (e: RuntimeException) {
                result = e
            }

            //THEN
            Assert.assertEquals(exceptionFromNetwork, result)

            Mockito.verify(roomDataSource).getBookList(loadSize)
            Mockito.verify(networkDataSource).getBookList(loadSize)
        }

    @Test
    fun `GIVEN a empty list from Room and an exception from Network WHEN getBookList is called THEN result is exception from network`() =
        runBlockingTest {
            //GIVEN
            val loadSize = 20
            Mockito.`when`(roomDataSource.getBookList(loadSize)).thenReturn(listOf())
            Mockito.`when`(networkDataSource.getBookList(loadSize)).thenThrow(exception)

            //WHEN
            var result: RuntimeException? = null
            try {
                repository.getBookList(loadSize)
            } catch (e: RuntimeException) {
                result = e
            }

            //THEN
            Assert.assertEquals(exception, result)

            Mockito.verify(roomDataSource).getBookList(loadSize)
            Mockito.verify(networkDataSource).getBookList(loadSize)
        }

    @Test
    fun `GIVEN a empty list from Network and an exception from Room WHEN getBookList is called THEN result is exception from Room`() =
        runBlockingTest {
            //GIVEN
            val loadSize = 20
            Mockito.`when`(roomDataSource.getBookList(loadSize)).thenThrow(exception)
            Mockito.`when`(networkDataSource.getBookList(loadSize)).thenReturn(listOf())

            //WHEN
            var result: RuntimeException? = null
            try {
                repository.getBookList(loadSize)
            } catch (e: RuntimeException) {
                result = e
            }

            //THEN
            Assert.assertEquals(exception, result)

            Mockito.verify(roomDataSource).getBookList(loadSize)
            Mockito.verify(networkDataSource).getBookList(loadSize)
        }

    @Test
    fun `GIVEN a book from Room WHEN getBookId is called THEN Network is not called`() =
        runBlockingTest {
            //GIVEN
            val id = "id"
            Mockito.`when`(roomDataSource.getBook(id)).thenReturn(book)

            //WHEN
            val result = repository.getBook(id)

            //THEN
            Assert.assertEquals(book, result)

            Mockito.verify(roomDataSource).getBook(id)
        }

    @Test
    fun `GIVEN an exception from Room WHEN getBookId is called THEN Network return a book`() =
        runBlockingTest {
            //GIVEN
            val id = "id"
            Mockito.`when`(roomDataSource.getBook(id)).thenThrow(exception)
            Mockito.`when`(networkDataSource.getBook(id)).thenReturn(book)

            //WHEN
            val result = repository.getBook(id)

            //THEN
            Assert.assertEquals(book, result)

            Mockito.verify(roomDataSource).getBook(id)
            Mockito.verify(networkDataSource).getBook(id)
        }

    @Test
    fun `WHEN getBookForList is called THEN Room return a book`() = runBlockingTest {
        //GIVEN
        val id = "id"
        Mockito.`when`(roomDataSource.getBookForList(id)).thenReturn(book)

        //WHEN
        val result = repository.getBookForList(id)

        //THEN
        Assert.assertEquals(book, result)

        Mockito.verify(roomDataSource).getBookForList(id)
    }
}