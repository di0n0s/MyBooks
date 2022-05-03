package com.example.books.domain.model

import com.example.books.BookTestUtils.detailDto
import com.example.books.BookTestUtils.entity
import com.example.books.BookTestUtils.listDto
import org.junit.Assert
import org.junit.Test

class BookTest {

    @Test
    fun `GIVEN a BookListDto WHEN fromBookListDto is called THEN return a Book`() {
        //GIVEN


        //WHEN
        val result = Book.fromBookListDto(listDto)

        //THEN
        Assert.assertEquals(listDto.id, result.id)
        Assert.assertEquals(listDto.title, result.title)
        Assert.assertEquals(null, result.author)
        Assert.assertEquals(listDto.imageUrl, result.imageUrl)
        Assert.assertEquals(null, result.price)
    }

    @Test
    fun `GIVEN a BookDetailDto WHEN fromBookDetailDto is called THEN return a Book`() {
        //GIVEN


        //WHEN
        val result = Book.fromBookDetailDto(detailDto)

        //THEN
        Assert.assertEquals(detailDto.id, result.id)
        Assert.assertEquals(detailDto.title, result.title)
        Assert.assertEquals(detailDto.author, result.author)
        Assert.assertEquals(detailDto.imageUrl, result.imageUrl)
        Assert.assertEquals(detailDto.price, result.price)
    }

    @Test
    fun `GIVEN a BookEntity WHEN fromBookEntity is called THEN return a Book`() {
        //GIVEN


        //WHEN
        val result = Book.fromBookEntity(entity)

        //THEN
        Assert.assertEquals(entity.id, result.id)
        Assert.assertEquals(entity.title, result.title)
        Assert.assertEquals(entity.author, result.author)
        Assert.assertEquals(entity.imageUrl, result.imageUrl)
        Assert.assertEquals(entity.price, result.price)
    }
}