package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.dto.BookDTO
import com.demo.bookrepkotlin.dto.ReportDTO
import com.demo.bookrepkotlin.service.ReportCService
import com.demo.bookrepkotlin.util.MainUtil
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.IOException


@Controller
class ReportCController @Autowired constructor(val reportCService: ReportCService){
    private val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("write")
    fun showReportWriter(@ModelAttribute("book") bookDTO: BookDTO?): String {
        log.info("showReportWriter()")
        return "write"
    }

    @GetMapping("book-search")
    @Throws(IOException::class, InterruptedException::class)
    fun getBookSearch(@RequestParam("keyword") keyword: String?,
                      model: Model,
                      @RequestParam(required = false) pageNum: Int): String {
        log.info("getBookSearch()")
        var currentPageNum: Int = 1
        if (pageNum != null){
            currentPageNum = pageNum
        }
        val bookList: List<BookDTO?> = reportCService.getBookByAPI(keyword)
        val bookPageList: List<Any?> = MainUtil.setPaging(bookList, currentPageNum)
        model.addAttribute("bookList", bookPageList)
        model.addAttribute("currentPageNum", currentPageNum)
        model.addAttribute("totalBookSize", bookList.size)
        model.addAttribute("keyword", keyword)
        return "bookSearch"
    }

    @GetMapping("apply")
    fun applyBookSearch(rttr: RedirectAttributes, bookDTO: BookDTO?): String {
        log.info("applyBookSearch()")
        reportCService.saveBook(bookDTO!!)

        rttr.addFlashAttribute("book", bookDTO)
        return "redirect:write"
    }

    @PostMapping("save")
    fun setReport(session: HttpSession?, reportDTO: ReportDTO?, rttr: RedirectAttributes?): String {
        log.info("setReport()")

        val view: String = reportCService.setReport(session!!, reportDTO, rttr!!)!!

        return view
    }
}