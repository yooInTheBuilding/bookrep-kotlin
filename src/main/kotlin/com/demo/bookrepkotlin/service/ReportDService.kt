package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.dao.ReportDao
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReportDService @Autowired constructor(val reportDao: ReportDao){
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun deleteReportByReportId(id: Long?) {
        log.info("deleteReportByReportId()")

        reportDao.deleteReportByReportId(id)
    }
}