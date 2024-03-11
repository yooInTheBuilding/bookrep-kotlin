package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.dao.ReportDao
import com.demo.bookrepkotlin.dto.ReportDTO
import com.demo.bookrepkotlin.util.MainUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class HomeService @Autowired constructor(val reportDao: ReportDao){
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun getReportToHome(email: String?, currentPageNum: Int): List<Any?> {
        log.info("getReportToHome()")

        val reportList: List<ReportDTO?>? = reportDao.getReportOfFollowing(email)

        val currentReportList: List<Any?> = MainUtil.setPaging(reportList!!, currentPageNum)

        return currentReportList
    }
}