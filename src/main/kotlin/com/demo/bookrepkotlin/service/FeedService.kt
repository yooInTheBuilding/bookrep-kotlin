package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.dao.BookDao
import com.demo.bookrepkotlin.dao.ReportDao
import com.demo.bookrepkotlin.dao.UserDao
import com.demo.bookrepkotlin.dto.ReportDTO
import com.demo.bookrepkotlin.util.MainUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class FeedService @Autowired constructor(val reportRService: ReportRService,
                                         val userDao: UserDao,
                                         val bookDao: BookDao,
                                         val reportDao: ReportDao){
    private val log = org.slf4j.LoggerFactory.getLogger(this.javaClass)

    fun getReportSummaryById(userEmail: String, loggedInUserEmail: String, pageNum: Int?): List<Any?>? {
        log.info("getRepostSummarybyId()")
        log.info("$userEmail and $loggedInUserEmail")
        var userReports: List<ReportDTO?>? = ArrayList()
        var currentPageNum: Int = 1
        if (pageNum != null){
            currentPageNum = pageNum
        }

        log.info("getReportSummaryById() 진입시도")
        try {
            userReports = reportDao.getReportSummaryById(userEmail)
            println(userReports)
        } catch (e: Exception) {
            log.info("getReportSummaryById에서 못 받아옴")
            e.printStackTrace()
        }

        val summaryList: MutableList<Any?> = ArrayList()
        log.info("이미지 매칭 작업 시작")

        try {
            for (reportDTO in userReports!!) {
                val image = bookDao.getImage(reportDTO!!.bookIsbn)

                val likeValue: Int? = reportRService.getLikeValueByReportId(reportDTO.id)

                val map: MutableMap<String, Any?> = HashMap()
                if (userEmail == loggedInUserEmail || reportDTO.publicBool) {
                    map["report"] = reportDTO
                    map["image"] = image
                    map["like"] = likeValue
                    summaryList.add(map)
                }
            }
            log.info("잘 가져옴")
        } catch (e: Exception) {
            log.info("이미지 못 받아옴.")
            e.printStackTrace()
        }


        log.info("setPaging() 진입시도")

        try {
            val reportSummaries: List<Any?> = MainUtil.setPaging(summaryList, currentPageNum)
            println(reportSummaries)
            return reportSummaries
        } catch (e: Exception) {
            log.info("setPaging에서 못 받아옴")
            e.printStackTrace()
            return null
        }
    }

    fun getReportValueById(userEmail: String?): Int {
        log.info("getReportValueById()")

        val userReports = reportDao.getReportSummaryById(userEmail)
        val reportValue = userReports!!.size

        return reportValue
    }

    fun getUserImage(userEmail: String?): String? {
        val userImage = userDao.getUserImage(userEmail)

        return userImage
    }

    fun getNameByEmail(userEmail: String?): String? {
        return userDao.getNameByEmail(userEmail)
    }
}