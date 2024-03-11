package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.service.FeedService
import com.demo.bookrepkotlin.service.HomeService
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


@Controller
class HomeController @Autowired constructor(val homeService: HomeService, val feedService: FeedService){
    private val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("/")
    fun main(): String {
        return "home1"
    }

    @GetMapping("/home")
    fun home(session: HttpSession, model: Model, @RequestParam(required = false) pageNum: Int): String {
        val email = session.getAttribute("email") as String

        log.info(email)

        if (email != null) {
            log.info("home2")
            var currentPageNum: Int = 1
            if (pageNum != null){
                currentPageNum = pageNum
            }

            val pageList: List<Any?> = homeService.getReportToHome(email, currentPageNum)

            model.addAttribute("sessionItems", pageList)
            return "home2"
        } else {
            log.info("home1")
            return "redirect:/"
        }
    }

    @PostMapping("get-image")
    @ResponseBody
    fun getImage(@RequestParam("email") email: String?): String? {
        log.info("getImage()")
        val imageAjax = feedService.getUserImage(email)
        return imageAjax
    }
}