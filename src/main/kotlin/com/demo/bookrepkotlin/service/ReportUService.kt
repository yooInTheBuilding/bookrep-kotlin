package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.dao.ReportDao
import com.demo.bookrepkotlin.dto.ReportDTO
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.ui.Model


@Service
class ReportUService @Autowired constructor(val reportDao: ReportDao){
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun isOwner(session: HttpSession, reportDTO: ReportDTO, model: Model): String {
        log.info("isOwner()")

        var view: String? = null
        val email = session.getAttribute("email") as String
        if (email != reportDTO.userEmail) {
            view = "redirect:report-detail?id=" + reportDTO.id
        } else {
            model.addAttribute("report", reportDTO)
            view = "reportUpdate"
        }

        return view
    }

    fun applyReportUpdate(reportDTO: ReportDTO?) {
        log.info("applyReportUpdate()")

        reportDao.applyReportUpdate(reportDTO)
    }
}