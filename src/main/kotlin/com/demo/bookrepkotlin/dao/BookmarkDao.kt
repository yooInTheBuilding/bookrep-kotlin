package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.BookmarkDTO
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Mapper
interface BookmarkDao {
    fun isBookmark(bookmarkDTO: BookmarkDTO?): Int

    fun setBookmark(bookmarkDTO: BookmarkDTO?)

    fun removeBookmark(bookmarkDTO: BookmarkDTO?)
}