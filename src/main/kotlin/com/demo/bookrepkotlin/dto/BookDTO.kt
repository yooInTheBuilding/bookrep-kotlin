package com.demo.bookrepkotlin.dto

data class BookDTO(
        var isbn: String,
        var name: String,
        var author: String,
        var publisher: String,
        var image: String
)
