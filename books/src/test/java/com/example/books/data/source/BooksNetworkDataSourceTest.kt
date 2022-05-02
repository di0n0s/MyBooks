package com.example.books.data.source

import com.example.books.data.dto.BookDetailDto
import com.example.books.data.dto.BookListDto
import com.example.books.data.service.MyBooksApiService
import com.example.books.data.source.BookTestUtils.entity
import com.example.books.domain.model.Book
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BooksNetworkDataSourceTest {

    private lateinit var dataSource: BooksNetworkDataSource

    private val ioDispatcher = TestCoroutineDispatcher()

    private val listDto = BookListDto(
        id = "id",
        title = "title",
        imageUrl = "imageUrl"
    )

    private val detailDto = BookDetailDto(
        id = "id",
        title = "title",
        imageUrl = "imageUrl",
        author = "author",
        price = 2.21
    )

    @Mock
    private lateinit var apiService: MyBooksApiService

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        dataSource = BooksNetworkDataSource(ioDispatcher, apiService)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(apiService)
    }

    @Test
    fun `GIVEN a list of BookListDto WHEN getBookList is called THEN return a list of Book and start plus listSize`() =
        runBlockingTest {
            //GIVEN
            val start = dataSource.getStart()
            val loadSize = 20
            val list = listOf(listDto)

            Mockito.`when`(apiService.getBookList(start, loadSize)).thenReturn(list)

            //WHEN
            val result = dataSource.getBookList(loadSize)

            //THEN
            Assert.assertEquals(start + list.size, dataSource.getStart())

            val dtoFromResult = result[0]
            val bookFromMap = list.map { dto -> Book.fromBookListDto(dto) }[0]
            Assert.assertEquals(dtoFromResult.id, bookFromMap.id)
            Assert.assertEquals(dtoFromResult.title, bookFromMap.title)
            Assert.assertEquals(dtoFromResult.author, bookFromMap.author)
            Assert.assertEquals(dtoFromResult.imageUrl, bookFromMap.imageUrl)
            Assert.assertEquals(dtoFromResult.price, bookFromMap.price)

            Mockito.verify(apiService).getBookList(start, loadSize)
        }

    @Test
    fun `GIVEN a BookDetailDto WHEN getBook is called THEN return a Book and start is not changed`() =
        runBlockingTest {
            //GIVEN
            val start = dataSource.getStart()
            val id = "id"

            Mockito.`when`(apiService.getBook(id)).thenReturn(detailDto)

            //WHEN
            val result = dataSource.getBook(id)

            //THEN
            Assert.assertEquals(start, dataSource.getStart())

            val book = Book.fromBookDetailDto(detailDto)
            Assert.assertEquals(result.id, book.id)
            Assert.assertEquals(result.title, book.title)
            Assert.assertEquals(result.author, book.author)
            Assert.assertEquals(result.imageUrl, book.imageUrl)
            Assert.assertEquals(result.price, book.price)

            Mockito.verify(apiService).getBook(id)
        }

    @Test
    fun `WHEN getBookForList is called THEN return a NotImplementedError`() =
        runBlockingTest {
            //GIVEN


            //WHEN
            var result: NotImplementedError? = null
            try {
                dataSource.getBookForList("id")
            } catch (e: NotImplementedError) {
                result = e
            }

            //THEN
            val error = NotImplementedError("An operation is not implemented: Not yet implemented")
            Assert.assertEquals(error.localizedMessage, result?.localizedMessage)
        }

    @Test
    fun `WHEN insertBook is called THEN return a NotImplementedError`() =
        runBlockingTest {
            //GIVEN


            //WHEN
            var result: NotImplementedError? = null
            try {
                dataSource.insertBook(entity)
            } catch (e: NotImplementedError) {
                result = e
            }

            //THEN
            val error = NotImplementedError("An operation is not implemented: Not yet implemented")
            Assert.assertEquals(error.localizedMessage, result?.localizedMessage)
        }
}