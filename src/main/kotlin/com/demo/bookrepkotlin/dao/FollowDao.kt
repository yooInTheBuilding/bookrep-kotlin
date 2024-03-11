package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.FollowDTO
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Mapper
interface FollowDao {
    fun getFollowerByEmail(email: String?): List<String?>?

    fun getFollowingByEmail(email: String?): List<String?>?

    fun isFollowing(followDTO: FollowDTO?): Int

    fun follow(followDTO: FollowDTO?)

    fun unfollow(followDTO: FollowDTO?)
}