package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.APIKEY
import com.demo.bookrepkotlin.dao.BookDao
import com.demo.bookrepkotlin.dao.ReportDao
import com.demo.bookrepkotlin.dto.BookDTO
import com.demo.bookrepkotlin.dto.ReportDTO
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.io.IOException
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


@Service
class ReportCService @Autowired constructor(val reportDao: ReportDao, val bookDao: BookDao){
    private val log = LoggerFactory.getLogger(this.javaClass)

    @Throws(IOException::class, InterruptedException::class)
    fun getBookByAPI(keyword: String?): List<BookDTO> {
        log.info("getBookByAPI()")

        val client: HttpClient = HttpClient.newHttpClient()
        val url = ("https://openapi.naver.com/v1/search/book.json"
                + "?query=" + URLEncoder.encode(keyword, "UTF-8")
                ).toString() + "&display=100"
        val request: HttpRequest = HttpRequest.newBuilder()
            .header("X-Naver-Client-Id", APIKEY.ID)
            .header("X-Naver-Client-Secret", APIKEY.SECRET)
            .uri(URI.create(url))
            .GET()
            .build()
        val response: HttpResponse<String> = client.send(request, HttpResponse.BodyHandlers.ofString())
        val responseBody = response.body()
        val naverBook = ObjectMapper().readValue(responseBody, NaverBook::class.java)
        val items = naverBook.items
        val bookList: MutableList<BookDTO> = ArrayList()
        try {
            for (item in items!!) {
                val bookDTO = BookDTO(
                    item.title!!, item.author!!, item.publisher!!, item.isbn!!,
                    item.image!!
                )
                bookList.add(bookDTO)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bookList
    }

    fun saveBook(bookDTO: BookDTO) {
        log.info("saveBook()")
        val bookDTO2 = bookDao.getBookByIsbn(bookDTO.isbn)
        if (bookDTO2 == null) {
            bookDao.saveBook(bookDTO)
        }
    }

    fun setReport(session: HttpSession, reportDTO: ReportDTO?, rttr: RedirectAttributes): String? {
        log.info("setReport()")

        var msg: String? = null
        var view: String? = null
        val email = session.getAttribute("email") as String
        if (email == null) {
            msg = "로그인 먼저 하세요"
            view = "redirect:sign-in"
        } else {
            try {
                reportDao.setReport(reportDTO)
                msg = "작성완료"
                view = "redirect:feed/" + session.getAttribute("email")
            } catch (e: Exception) {
                e.printStackTrace()
                msg = "다시 입력해주세요"
                view = "redirect:write"
            }
        }

        rttr.addFlashAttribute(msg!!)

        return view
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    class NaverBook {
        val items: List<Item>? = null


        @JsonIgnoreProperties(ignoreUnknown = true)
        class Item {
            val title: String? = null

            val author: String? = null

            val publisher: String? = null

            val isbn: String? = null

            val image: String? = null
        }
    }
}