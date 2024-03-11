package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.BookDTO
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Mapper
interface BookDao {
    fun getBookList(keyword: String?): List<BookDTO?>?

    fun getImage(bookIsbn: String?): String?

    fun saveBook(bookDTO: BookDTO?)

    fun getBookByIsbn(isbn: String?): BookDTO?

    fun getBookmarkByEmail(email: String?): List<BookDTO?>?
}