package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.dao.BookDao
import com.demo.bookrepkotlin.dao.BookmarkDao
import com.demo.bookrepkotlin.dto.BookDTO
import com.demo.bookrepkotlin.dto.BookmarkDTO
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class BookmarkService @Autowired constructor(val bookmarkDao: BookmarkDao, val bookDao: BookDao){
    private val log = org.slf4j.LoggerFactory.getLogger(this.javaClass)

    fun isBookmark(session: HttpSession, isbn: String?): Boolean {
        log.info("isBookmark()")

        val email = session.getAttribute("email") as String

        val bookmarkDTO = BookmarkDTO(email, isbn!!)

        val isBookmark = bookmarkDao.isBookmark(bookmarkDTO)

        var isBookmarkBool = false
        if (isBookmark != 0) {
            isBookmarkBool = true
        }

        return isBookmarkBool
    }


    fun setBookmark(session: HttpSession, isbn: String?) {
        log.info("setBookmark()")

        val email = session.getAttribute("email") as String
        val bookmarkDTO = BookmarkDTO(email, isbn!!)

        bookmarkDao.setBookmark(bookmarkDTO)
    }


    fun getBookmarkByEmail(email: String?): List<BookDTO?>? {
        log.info("getBookmarkByEmail")

        val bookmarkList = bookDao.getBookmarkByEmail(email)

        return bookmarkList
    }

    fun removeBookmark(session: HttpSession, isbn: String?) {
        log.info("removeBookmark()")

        val email = session.getAttribute("email") as String
        val bookmarkDTO = BookmarkDTO(email, isbn!!)

        bookmarkDao.removeBookmark(bookmarkDTO)
    }

}