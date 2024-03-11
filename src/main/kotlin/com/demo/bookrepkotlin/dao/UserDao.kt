package com.demo.bookrepkotlin.dao

import com.demo.bookrepkotlin.dto.UserDTO
import org.apache.ibatis.annotations.Select


interface UserDao {
    @Select("SELECT count(*) FROM user")
    fun getUserCnt(): Int

    fun getUserList(keyword: String?): List<UserDTO?>?

    fun signIn(map: Map<String?, String?>?): Int

    fun getUserImage(userEmail: String?): String?

    fun emailCheck(email: String?): Int

    fun applySignUp(userDTO: UserDTO?)

    fun getPassword(map: Map<String?, String?>?): String?

    fun showModify(email: String?): UserDTO?

    fun modify(userDTO: UserDTO?)

    fun resign(email: String?)

    fun getNameByEmail(userEmail: String?): String?
}