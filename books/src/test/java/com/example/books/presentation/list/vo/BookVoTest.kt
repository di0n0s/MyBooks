package com.example.books.presentation.list.vo

import android.content.res.Resources
import com.example.books.BookTestUtils.listDto
import com.example.books.domain.model.Book
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class BookVoTest {

    @Test
    fun `GIVEN a Book WHEN fromBook is called THEN return a BookVo`() {
        //GIVEN


        //WHEN
        val result = BookPaginationVo.BookVo.fromBook(Book.fromBookListDto(listDto))

        //THEN
        Assert.assertEquals(listDto.id, result.id)
        Assert.assertEquals(listDto.title, result.title)

        val widthPixels = Resources.getSystem().displayMetrics.widthPixels / 3
        Assert.assertEquals("""${listDto.imageUrl}$widthPixels""", result.imageUrl)
    }
}