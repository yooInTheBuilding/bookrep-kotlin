package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.dao.BookDao
import com.demo.bookrepkotlin.dao.ReportDao
import com.demo.bookrepkotlin.dto.BookDTO
import com.demo.bookrepkotlin.dto.ReportDTO
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
@Slf4j
class BookService @Autowired constructor(val bookDao: BookDao, val reportDao: ReportDao){

    private val log = org.slf4j.LoggerFactory.getLogger(this.javaClass)
    fun getBookByIsbn(isbn: String?): BookDTO? {
        log.info("getBookByIsbn()")

        val bookDTO = bookDao.getBookByIsbn(isbn)

        return bookDTO
    }

    fun getReportByIsbn(isbn: String?): List<ReportDTO?>? {
        log.info("getReportByIsbn()")

        val reportList = reportDao.getReportByIsbn(isbn)

        return reportList
    }
}