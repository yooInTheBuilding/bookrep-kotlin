package com.demo.bookrepkotlin.dto

data class BookDTO(
        private var isbn : String,
        private var name : String,
        private var author : String,
        private var publisher : String,
        private var image : String
)
