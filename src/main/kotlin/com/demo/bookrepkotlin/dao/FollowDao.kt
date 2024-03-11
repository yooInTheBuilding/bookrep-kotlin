package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.FollowDTO


interface FollowDao {
    fun getFollowerByEmail(email: String?): List<String?>?

    fun getFollowingByEmail(email: String?): List<String?>?

    fun isFollowing(followDTO: FollowDTO?): Int

    fun follow(followDTO: FollowDTO?)

    fun unfollow(followDTO: FollowDTO?)
}