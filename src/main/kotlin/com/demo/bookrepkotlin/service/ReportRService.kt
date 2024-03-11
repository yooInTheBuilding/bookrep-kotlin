package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.dao.CommentDao
import com.demo.bookrepkotlin.dao.LikeDao
import com.demo.bookrepkotlin.dao.ReportDao
import com.demo.bookrepkotlin.dto.CommentDTO
import com.demo.bookrepkotlin.dto.LikeDTO
import com.demo.bookrepkotlin.dto.ReportDTO
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ReportRService @Autowired constructor(val reportDao: ReportDao, val likeDao: LikeDao, val commentDao: CommentDao){
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun getReportDetailByReportId(id: Long?): ReportDTO? {
        log.info("getReportDetailByReportId()")

        val reportDTO = reportDao.getReportDetailByReportId(id)

        return reportDTO
    }

    fun getCommentByReportId(id: Long?): List<CommentDTO?>? {
        log.info("getCommentByReportId()")

        val commentList = commentDao.getCommentByReportId(id)

        return commentList
    }

    fun getLikeValueByReportId(id: Long?): Int? {
        log.info("getLikeValueByReportId()")

        val likeValue = likeDao.getLikeValueByReportId(id)

        return likeValue
    }

    fun setLike(email: String?, id: Long?) {
        log.info("setLike()")

        val likeDTO = LikeDTO(email!!, id!!)

        val likeValue = likeDao.isLike(likeDTO)


        // 좋아요 토글
        if (likeValue > 0) {
            likeDao.removeLike(likeDTO)
        } else {
            likeDao.setLike(likeDTO)
        }
    }

    fun setComment(email: String?, id: Long?, content: String?) {
        log.info("setComment()")

        val commentDTO = CommentDTO(0L, "", "", 0L, LocalDateTime.now())
        commentDTO.reportId = id!!
        commentDTO.content = content!!
        commentDTO.userEmail = email!!
        commentDao.setComment(commentDTO)
    }

    fun isLike(email: String?, id: Long?): Int {
        val likeDTO = LikeDTO(email!!, id!!)

        val isLike = likeDao.isLike(likeDTO)

        return isLike
    }
}