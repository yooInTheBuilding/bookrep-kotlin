package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.BookmarkDTO


interface BookmarkDao {
    fun isBookmark(bookmarkDTO: BookmarkDTO?): Int

    fun setBookmark(bookmarkDTO: BookmarkDTO?)

    fun removeBookmark(bookmarkDTO: BookmarkDTO?)
}