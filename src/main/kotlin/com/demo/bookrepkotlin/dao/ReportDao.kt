package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.ReportDTO
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Mapper
interface ReportDao {
    fun setReport(reportDTO: ReportDTO?)

    fun getReportDetailByReportId(id: Long?): ReportDTO?

    fun deleteReportByReportId(id: Long?)

    fun applyReportUpdate(reportDTO: ReportDTO?)

    fun getReportSummaryById(userEmail: String?): List<ReportDTO?>?

    fun getReportOfFollowing(email: String?): List<ReportDTO?>?

    fun getReportByIsbn(isbn: String?): List<ReportDTO?>?
}