package com.demo.bookrepkotlin.dto

import java.time.LocalDateTime

data class ReportDTO(
        var id: Long,
        var title: String,
        var content: String,
        var userEmail: String,
        var time: LocalDateTime,
        var publicBool: Boolean,
        var bookIsbn: String
)
