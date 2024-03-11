package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.ReportDTO


interface ReportDao {
    fun setReport(reportDTO: ReportDTO?)

    fun getReportDetailByReportId(id: Long?): ReportDTO?

    fun deleteReportByReportId(id: Long?)

    fun applyReportUpdate(reportDTO: ReportDTO?)

    fun getReportSummaryById(userEmail: String?): List<ReportDTO?>?

    fun getReportOfFollowing(email: String?): List<ReportDTO?>?

    fun getReportByIsbn(isbn: String?): List<ReportDTO?>?
}