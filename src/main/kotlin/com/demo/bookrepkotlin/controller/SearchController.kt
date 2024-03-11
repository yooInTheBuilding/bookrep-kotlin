package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.dto.BookDTO
import com.demo.bookrepkotlin.dto.UserDTO
import com.demo.bookrepkotlin.service.SearchService
import com.demo.bookrepkotlin.util.MainUtil
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class SearchController @Autowired constructor(val searchService: SearchService){
    private val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("search")
    @Throws(JsonProcessingException::class)
    fun getTotalSearchByString(
        @RequestParam("keyword") keyword: String?,
        model: Model,
        @RequestParam(required = false) userPageNum: Int?,
        @RequestParam(required = false) bookPageNum: Int?
    ): String {
        log.info("controller.getTotalSearchByString()")

        val userList: List<UserDTO?>? = searchService.getUserByString(keyword)
        val bookList: List<BookDTO?>? = searchService.getBookByString(keyword)
        for (i in userList!!.indices) {
            log.info(userList[i]!!.name)
        }
        for (i in bookList!!.indices) {
            log.info(bookList[i]!!.name)
        }
        val currentUserList: List<UserDTO?>
        val currentBookList: List<BookDTO?>

        if (userPageNum == null) {
            currentUserList = MainUtil.setPagingUser(userList, 1)
            model.addAttribute("currentUserPageNum", 1)
        } else {
            currentUserList = MainUtil.setPagingUser(userList, userPageNum)
            model.addAttribute("currentUserPageNum", userPageNum)
        }
        if (bookPageNum == null) {
            currentBookList = MainUtil.setPagingBook(bookList, 1)
            model.addAttribute("currentBookPageNum", 1)
        } else {
            currentBookList = MainUtil.setPagingBook(bookList, bookPageNum)
            model.addAttribute("currentBookPageNum", bookPageNum)
        }

        model.addAttribute("userList", currentUserList)
        model.addAttribute("bookList", currentBookList)
        model.addAttribute("keyword", keyword)
        model.addAttribute("totalUserSize", userList.size)
        model.addAttribute("totalBookSize", bookList.size)
        return "search"
    }

    @PostMapping("user-result")
    @ResponseBody
    @Throws(JsonProcessingException::class)
    fun getUserResult(@RequestParam("keyword") keyword: String?, @RequestParam("pageNum") pageNum: Int): String {
        log.info("getUserResult()")

        val userList: List<UserDTO?>? = searchService.getUserByString(keyword)


        val userListJson = ObjectMapper().writeValueAsString(userList)

        return userListJson
    }

    @PostMapping("book-result")
    @ResponseBody
    @Throws(JsonProcessingException::class)
    fun getBookResult(@RequestParam("keyword") keyword: String?, @RequestParam("pageNum") pageNum: Int): String {
        log.info("getBookResult()")

        val bookList: List<BookDTO?>? = searchService.getBookByString(keyword)
        val bookListJson = ObjectMapper().writeValueAsString(bookList)

        return bookListJson
    }
}