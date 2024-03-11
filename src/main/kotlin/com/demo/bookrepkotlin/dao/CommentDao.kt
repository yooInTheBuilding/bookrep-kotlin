package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.CommentDTO


interface CommentDao {
    fun getCommentByReportId(id: Long?): List<CommentDTO?>?

    fun setComment(commentDTO: CommentDTO?)
}