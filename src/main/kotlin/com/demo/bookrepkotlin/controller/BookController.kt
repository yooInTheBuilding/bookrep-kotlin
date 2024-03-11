package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.dto.BookDTO
import com.demo.bookrepkotlin.dto.ReportDTO
import com.demo.bookrepkotlin.service.BookService
import com.demo.bookrepkotlin.service.BookmarkService
import com.demo.bookrepkotlin.util.MainUtil
import jakarta.servlet.http.HttpSession
import lombok.extern.slf4j.Slf4j
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@Slf4j
class BookController @Autowired constructor(val bookService: BookService, val bookmarkService: BookmarkService){

    private val log = LoggerFactory.getLogger(this.javaClass)
    @GetMapping("book-detail")
    fun showBookDetail(session: HttpSession?,
                       @RequestParam("isbn") isbn: String?,
                       model: Model,
                       @RequestParam(required = false) pageNum: Int?): String {
        log.info("showBookDetail()")

        var currentPageNum : Int? = 1
        if (pageNum != null) {
            currentPageNum = pageNum
        }
        val isBookmark: Boolean = bookmarkService.isBookmark(session!!, isbn)

        val bookDTO: BookDTO? = bookService.getBookByIsbn(isbn)

        val reportList: List<ReportDTO?>? = bookService.getReportByIsbn(isbn)
        val currentReportList = MainUtil.setPaging(reportList!!, currentPageNum!!)

        model.addAttribute("reportList", currentReportList)
        model.addAttribute("currentPageNum", currentPageNum)
        model.addAttribute("totalReportSize", reportList.size)
        model.addAttribute("isBookmark", isBookmark)
        model.addAttribute("book", bookDTO)

        return "bookDetail"
    }

    @PostMapping("bookmark")
    @ResponseBody
    fun setBookmark(session: HttpSession?, @RequestParam("isbn") isbn: String?): Int {
        log.info("setBookmark()")

        val bookmarkBool: Boolean = bookmarkService.isBookmark(session!!, isbn)
        if (bookmarkBool) {
            bookmarkService.removeBookmark(session, isbn)
            return 0
        } else {
            bookmarkService.setBookmark(session, isbn)
            return 1
        }
    }
}