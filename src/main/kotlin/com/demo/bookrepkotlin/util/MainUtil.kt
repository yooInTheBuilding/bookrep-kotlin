package com.demo.bookrepkotlin.util

import com.demo.bookrepkotlin.dto.BookDTO
import com.demo.bookrepkotlin.dto.UserDTO
import org.slf4j.LoggerFactory


class MainUtil {
    companion object {
        private var maxNum = 0

        val log = LoggerFactory.getLogger(this.javaClass)

        fun setPagingUser(userList: List<UserDTO?>, userPageNum: Int): List<UserDTO?> {
            log.info("setPagingUser()")

            val from = 6 * (userPageNum - 1)
            val end = if (from + 5 > userList.size - 1) {
                userList.size
            } else {
                from + 6
            }
            val currentUserList = userList.subList(from, end)
            return currentUserList
        }

        fun setPagingBook(bookList: List<BookDTO?>, bookPageNum: Int): List<BookDTO?> {
            log.info("setPagingBook()")

            val from = 6 * (bookPageNum - 1)
            val end = if (from + 5 > bookList.size - 1) {
                bookList.size
            } else {
                from + 6
            }
            val currentBookList = bookList.subList(from, end)
            return currentBookList
        }

        fun setPagingFollow(followingList: List<String?>, currentPage: Int): List<String?> {
            log.info("setPagingFollow()")

            val from = 6 * (currentPage - 1)
            val end = if (from + 5 > followingList.size - 1) {
                followingList.size
            } else {
                from + 6
            }
            val currentFollowingList = followingList.subList(from, end)

            return currentFollowingList
        }

        fun setPaging(list: List<Any?>, pageNum: Int): List<Any?> {
            log.info("setPaging()")

            val from = 6 * (pageNum - 1)
            val end = if (from + 5 > list.size - 1) {
                list.size
            } else {
                from + 6
            }
            val currentList = list.subList(from, end)

            return currentList
        }
    }
}