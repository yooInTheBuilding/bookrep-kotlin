package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.dto.ReportDTO
import com.demo.bookrepkotlin.service.ReportRService
import com.demo.bookrepkotlin.service.ReportUService
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam


@Controller
class ReportUController @Autowired constructor(val reportRService: ReportRService, val reportUService: ReportUService){
    private val log = LoggerFactory.getLogger(this.javaClass)

    @GetMapping("report-update")
    fun showReportUpdate(session: HttpSession?, @RequestParam("id") id: Long?, model: Model?): String {
        log.info("showReportUpdate()")

        val reportDTO = reportRService.getReportDetailByReportId(id)
        val view: String = reportUService.isOwner(session!!, reportDTO!!, model!!)

        return view
    }

    @PostMapping("/apply-update")
    fun applyReportUpdate(@ModelAttribute reportDTO: ReportDTO): String {
        log.info("applyReportUpdate()")

        reportUService.applyReportUpdate(reportDTO)

        return "redirect:/report-detail?id=" + reportDTO.id
    }
}