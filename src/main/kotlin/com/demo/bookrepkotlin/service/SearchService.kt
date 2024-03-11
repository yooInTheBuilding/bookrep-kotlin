package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.dao.BookDao
import com.demo.bookrepkotlin.dao.UserDao
import com.demo.bookrepkotlin.dto.BookDTO
import com.demo.bookrepkotlin.dto.UserDTO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class SearchService @Autowired constructor(val userDao: UserDao, val bookDao: BookDao){
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun getUserByString(keyword: String?): List<UserDTO?>? {
        log.info("service.getUserByString()")
        val userList = userDao.getUserList(keyword)

        return userList
    }

    fun getBookByString(keyword: String?): List<BookDTO?>? {
        log.info("service.getBookByString()")
        val bookList = bookDao.getBookList(keyword)

        return bookList
    }
}