package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.service.ReportDService
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class ReportDController @Autowired constructor(val reportDService: ReportDService){
    private val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("delete")
    fun deleteReportByReportId(
        @RequestParam("id") id: Long,
        session: HttpSession,
        rttr: RedirectAttributes,
        @RequestParam("reportUserEmail") reportEmail: String
    ): String? {
        log.info("deleteReportByReportId()")

        val email = session.getAttribute("email") as String
        var msg: String? = null
        var view: String? = null
        if (email == null || email != reportEmail) {
            msg = "you are not an owner of this report"
            view = "redirect:/report-detail?id=$id"
        } else {
            try {
                reportDService.deleteReportByReportId(id)
                msg = "delete complete"
                view = "redirect:/"
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "delete failed"
                view = "redirect:/report-detail?id=$id"
            }
        }
        rttr.addFlashAttribute("msg", msg)
        return view
    }
}