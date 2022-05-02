package com.example.books.data.source

import com.example.books.data.entity.BookEntity
import com.example.books.data.room.BooksDao
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
class BooksRoomDataSourceTest {

    private lateinit var dataSource: BooksRoomDataSource

    private val ioDispatcher = TestCoroutineDispatcher()

    private val entity = BookEntity(
        id = "id",
        title = "title",
        imageUrl = "imageUrl",
        author = "author",
        price = 2.01
    )


    @Mock
    private lateinit var dao: BooksDao

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        dataSource = BooksRoomDataSource(dao = dao, ioDispatcher = ioDispatcher)
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(dao)
    }

    @Test
    fun `GIVEN an offset and loadSize WHEN getBookList is called THEN return a list of Book and start plus listSize`() =
        runBlockingTest {
            //GIVEN
            val start = dataSource.getStart()
            val loadSize = 20
            val list = listOf(entity)

            Mockito.`when`(dao.getBookList(start, loadSize)).thenReturn(list)

            //WHEN
            val result = dataSource.getBookList(loadSize)

            //THEN
            Assert.assertEquals(start + list.size, dataSource.getStart())

            val entityFromResult = result[0]
            val bookFromMap = list.map { entity -> Book.fromBookEntity(entity) }[0]
            Assert.assertEquals(entityFromResult.id, bookFromMap.id)
            Assert.assertEquals(entityFromResult.title, bookFromMap.title)
            Assert.assertEquals(entityFromResult.author, bookFromMap.author)
            Assert.assertEquals(entityFromResult.imageUrl, bookFromMap.imageUrl)
            Assert.assertEquals(entityFromResult.price, bookFromMap.price)

            Mockito.verify(dao).getBookList(start, loadSize)
        }

    @Test
    fun `GIVEN an id WHEN getBook is called THEN return a Book and start is not changed`() =
        runBlockingTest {
            //GIVEN
            val start = dataSource.getStart()
            val id = "id"

            Mockito.`when`(dao.getBook(id)).thenReturn(entity)

            //WHEN
            val result = dataSource.getBook(id)

            //THEN
            Assert.assertEquals(start, dataSource.getStart())

            val book = Book.fromBookEntity(entity)
            Assert.assertEquals(result.id, book.id)
            Assert.assertEquals(result.title, book.title)
            Assert.assertEquals(result.author, book.author)
            Assert.assertEquals(result.imageUrl, book.imageUrl)
            Assert.assertEquals(result.price, book.price)

            Mockito.verify(dao).getBook(id)
        }

    @Test
    fun `GIVEN an id WHEN getBook is called THEN return a Book and start plus one`() =
        runBlockingTest {
            //GIVEN
            val start = dataSource.getStart()
            val id = "id"

            Mockito.`when`(dao.getBook(id)).thenReturn(entity)

            //WHEN
            val result = dataSource.getBookForList(id)

            //THEN
            Assert.assertEquals(start + 1, dataSource.getStart())

            val book = Book.fromBookEntity(entity)
            Assert.assertEquals(result.id, book.id)
            Assert.assertEquals(result.title, book.title)
            Assert.assertEquals(result.author, book.author)
            Assert.assertEquals(result.imageUrl, book.imageUrl)
            Assert.assertEquals(result.price, book.price)

            Mockito.verify(dao).getBook(id)
        }

    @Test
    fun `GIVEN a success WHEN insertBook is called THEN return true and start is not changed`() =
        runBlockingTest {
            //GIVEN
            val start = dataSource.getStart()

            Mockito.`when`(dao.insertBook(entity)).thenReturn(1)

            //WHEN
            val result = dataSource.insertBook(entity)

            //THEN
            Assert.assertEquals(start, dataSource.getStart())

            Assert.assertTrue(result)

            Mockito.verify(dao).insertBook(entity)
        }

    @Test
    fun `GIVEN an error WHEN insertBook is called THEN return false and start is not changed`() =
        runBlockingTest {
            //GIVEN
            val start = dataSource.getStart()

            Mockito.`when`(dao.insertBook(entity)).thenReturn(0)

            //WHEN
            val result = dataSource.insertBook(entity)

            //THEN
            Assert.assertEquals(start, dataSource.getStart())

            Assert.assertFalse(result)

            Mockito.verify(dao).insertBook(entity)
        }
}