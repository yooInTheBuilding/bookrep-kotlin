package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.dto.BookDTO
import com.demo.bookrepkotlin.service.BookmarkService
import com.demo.bookrepkotlin.util.MainUtil
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam


@Controller
@Slf4j
class BookmarkController @Autowired constructor(val bookmarkService: BookmarkService){
    private val log = org.slf4j.LoggerFactory.getLogger(this.javaClass)

    @GetMapping("bookmark/{email}")
    fun showBookmark(@PathVariable email: String?,
                     model: Model,
                     @RequestParam(required = false) pageNum: Int?): String {
        log.info("showBookmark()")

        val bookList: List<BookDTO?>? = bookmarkService.getBookmarkByEmail(email)
        var currentPageNum: Int? = 1
        if (pageNum != null) {
            currentPageNum = pageNum
        }
        val currentBookList = MainUtil.setPaging(bookList!!, currentPageNum!!)
        model.addAttribute("bookmarkList", currentBookList)
        model.addAttribute("currentPageNum", currentPageNum)
        model.addAttribute("email", email)
        model.addAttribute("totalBookSize", bookList.size)
        return "bookmark"
    }
}