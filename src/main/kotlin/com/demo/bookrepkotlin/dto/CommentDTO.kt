package com.demo.bookrepkotlin.dto

import java.time.LocalDateTime

data class CommentDTO(
        var id: Long,
        var content: String,
        var userEmail: String,
        var reportId: Long,
        var time: LocalDateTime
)
