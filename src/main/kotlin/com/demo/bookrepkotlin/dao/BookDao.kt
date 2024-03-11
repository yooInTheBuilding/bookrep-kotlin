package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.BookDTO


interface BookDao {
    fun getBookList(keyword: String?): List<BookDTO?>?

    fun getImage(bookIsbn: String?): String?

    fun saveBook(bookDTO: BookDTO?)

    fun getBookByIsbn(isbn: String?): BookDTO?

    fun getBookmarkByEmail(email: String?): List<BookDTO?>?
}