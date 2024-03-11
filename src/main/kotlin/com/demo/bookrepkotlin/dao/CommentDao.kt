package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.CommentDTO
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Mapper
interface CommentDao {
    fun getCommentByReportId(id: Long?): List<CommentDTO?>?

    fun setComment(commentDTO: CommentDTO?)
}