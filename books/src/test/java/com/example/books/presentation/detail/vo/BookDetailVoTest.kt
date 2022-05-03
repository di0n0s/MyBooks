package com.example.books.presentation.detail.vo

import android.content.res.Resources
import com.example.books.BookTestUtils.detailDto
import com.example.books.domain.model.Book
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.text.DecimalFormat

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class BookDetailVoTest {

    @Test
    fun `GIVEN a Book WHEN fromBook is called THEN return a BookVo`() {
        //GIVEN


        //WHEN
        val result = BookDetailVo.fromBook(Book.fromBookDetailDto(detailDto))

        //THEN
        Assert.assertEquals(detailDto.id, result.id)
        Assert.assertEquals(detailDto.title, result.title)
        Assert.assertEquals(detailDto.author, result.author)
        Assert.assertEquals("""${detailDto.price.formatPrice()} €""", result.price)

        val widthPixels = Resources.getSystem().displayMetrics.widthPixels
        Assert.assertEquals("""${detailDto.imageUrl}$widthPixels""", result.imageUrl)
    }

    private fun Double.formatPrice(): String {
        val format = DecimalFormat("0.#")
        return format.format(this)
    }
}