package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.dto.CommentDTO
import com.demo.bookrepkotlin.dto.ReportDTO
import com.demo.bookrepkotlin.service.ReportRService
import com.demo.bookrepkotlin.util.MainUtil
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class ReportRController @Autowired constructor(val reportRService: ReportRService){
    private val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("report-detail")
    fun showReportDetail(@RequestParam("id") id: Long?,
                         session: HttpSession,
                         model: Model,
                         @RequestParam(required = false) pageNum: Int): String {
        log.info("showReportDetail()")

        val loggedInUserEmail = session.getAttribute("email") as String

        var currentPageNum: Int = 1
        if (pageNum != null){
            currentPageNum = pageNum
        }

        val reportDTO: ReportDTO = reportRService.getReportDetailByReportId(id)!!
        val commentList: List<CommentDTO?> = reportRService.getCommentByReportId(id)!!
        val pageList: List<Any?> = MainUtil.setPaging(commentList, currentPageNum)
        val likeValue: Int = reportRService.getLikeValueByReportId(id)!!
        val isLike: Int = reportRService.isLike(loggedInUserEmail, id)

        var isLikeBool = false
        if (isLike == 1) {
            isLikeBool = true
        }

        model.addAttribute("report", reportDTO)
        model.addAttribute("commentList", pageList)
        model.addAttribute("likeValue", likeValue)
        model.addAttribute("isLike", isLikeBool)
        model.addAttribute("currentPageNum", currentPageNum)
        model.addAttribute("totalCommentSize", commentList.size)



        return "reportDetail"
    }

    @PostMapping("like")
    fun setLike(@RequestParam("id") id: Long, session: HttpSession): String {
        log.info("setLike()")

        val email = session.getAttribute("email") as String

        reportRService.setLike(email, id)

        return "redirect: report-detail?id=$id"
    }

    @PostMapping("comment")
    fun setComment(
        @RequestParam("id") id: Long,
        session: HttpSession,
        @RequestParam("comment") comment: String?
    ): String {
        log.info("setComment()")

        val email = session.getAttribute("email") as String

        reportRService.setComment(email, id, comment)

        return "redirect: report-detail?id=$id"
    }
}