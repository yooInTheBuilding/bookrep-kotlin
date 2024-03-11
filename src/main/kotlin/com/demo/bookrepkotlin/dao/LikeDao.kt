package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.LikeDTO
import org.apache.ibatis.annotations.Mapper
import org.springframework.stereotype.Repository

@Mapper
interface LikeDao {
    fun getLikeValueByReportId(id: Long?): Int?

    fun setLike(likeDTO: LikeDTO?)

    fun removeLike(likeDTO: LikeDTO?)

    fun isLike(likeDTO: LikeDTO?): Int

}