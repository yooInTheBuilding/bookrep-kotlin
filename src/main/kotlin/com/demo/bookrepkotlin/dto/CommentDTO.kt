package com.demo.bookrepkotlin.dto

import java.time.LocalDateTime

data class CommentDTO(
        private var id : Long,
        private var content : String,
        private var userEmail : String,
        private var reportId : Long,
        private var time : LocalDateTime
)
