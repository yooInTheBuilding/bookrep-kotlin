package com.demo.bookrepkotlin.dto

import java.time.LocalDateTime

data class ReportDTO(
        private var id : Long,
        private var title : String,
        private var content : String,
        private var userEmail : String,
        private var time : LocalDateTime,
        private var publicBool : Boolean
)
