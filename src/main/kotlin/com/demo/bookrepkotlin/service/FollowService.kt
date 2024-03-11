package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.dao.FollowDao
import com.demo.bookrepkotlin.dao.UserDao
import com.demo.bookrepkotlin.dto.FollowDTO
import com.demo.bookrepkotlin.dto.UserDTO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class FollowService @Autowired constructor(val followDao: FollowDao, val userDao: UserDao){
    private val log = org.slf4j.LoggerFactory.getLogger(this.javaClass)

    fun getFollowerValueByEmail(userEmail: String?): Int {
        log.info("getFollowerValueById()")


        // 페이지 유저에 따른 팔로워 리스트 가져오기
        val followerList = getFollowerByEmail(userEmail)

        if (followerList.isNullOrEmpty()) {
            return 0
        }


        // 팔로워 수 저장
        val followerCnt = followerList.size

        return followerCnt
    }

    fun getFollowingValueByEmail(userEmail: String?): Int {
        // 페이지 유저에 따른 팔로잉 리스트 가져오기
        val followingList = getFollowingByEmail(userEmail)

        if (followingList.isNullOrEmpty()) {
            return 0
        }


        // 팔로잉 수 저장
        val followingCnt = followingList.size

        return followingCnt
    }

    fun getFollowerByEmail(email: String?): List<String?>? {
        log.info("getFollowerByEmail()")

        val followerList = followDao.getFollowerByEmail(email)

        return followerList
    }

    fun getFollowingByEmail(email: String?): List<String?>? {
        log.info("getFollowingByEmail()")

        val followingList = followDao.getFollowingByEmail(email)

        return followingList
    }

    fun follow(followerEmail: String?, followeeEmail: String?) {
        log.info("follow()")

        val followDTO = FollowDTO(followerEmail!!, followeeEmail!!)
        followDao.follow(followDTO)
    }

    fun unfollow(followerEmail: String?, followeeEmail: String?) {
        log.info("unfollow()")

        val followDTO = FollowDTO(followerEmail!!, followeeEmail!!)
        followDao.unfollow(followDTO)
    }

    fun isFollowing(followerEmail: String?, followeeEmail: String?): Boolean {
        log.info("isFollowing()")

        val followDTO = FollowDTO(followerEmail!!, followeeEmail!!)
        val followValue = followDao.isFollowing(followDTO)

        return if (followValue == 0) {
            false
        } else {
            true
        }
    }

    fun getUserByEmail(userEmail: String?): UserDTO? {
        log.info("getUserByEmail()")
        return userDao.showModify(userEmail)
    }
}