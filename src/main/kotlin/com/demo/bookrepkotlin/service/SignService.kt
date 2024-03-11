package com.demo.bookrepkotlin.service

import com.demo.bookrepkotlin.dao.UserDao
import com.demo.bookrepkotlin.dto.UserDTO
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File


@Service
class SignService @Autowired constructor(val userDao: UserDao){
    private val log = LoggerFactory.getLogger(this.javaClass)

    fun signIn(session: HttpSession, email: String?, password: String?): String {
        log.info("signIn()")

        val map: MutableMap<String?, String?> = HashMap()
        map["email"] = email
        map["password"] = password
        val loginResult = userDao.signIn(map)

        if (loginResult != 0) {
            log.info("로그인 성공")

            session.setAttribute("email", email)

            return "redirect:/home"
        } else {
            log.info("로그인 실패")
            return "signIn"
        }
    }

    fun emailCheck(email: String?): Int {
        log.info("emailCheck()")

        val cnt = userDao.emailCheck(email)
        return cnt
    }

    fun applySignUp(email: String?, name: String?, password: String?): Array<String> {
        log.info("applySignUp()")

        var msg = "회원가입 성공"
        var view = "redirect:/"
        val userDTO = UserDTO(email!!, password!!, name!!, "")

        try {
            userDao.applySignUp(userDTO)
        } catch (e: Exception) {
            e.printStackTrace()
            msg = "중복된 이메일입니다"
            view = "redirect:sign-up"
        }
        val arr = arrayOf(msg, view)

        return arr
    }

    fun findPassword(email: String?, name: String?): String {
        log.info("findPassword()")

        var result: String? = null

        val map: MutableMap<String?, String?> = HashMap()
        map["email"] = email
        map["name"] = name
        val password = userDao.getPassword(map)
        result = if (password.isNullOrEmpty()) {
            "입력정보가 틀렸습니다."
        } else {
            "당신의 비밀번호는 $password 입니다."
        }

        return result
    }

    fun showModify(session: HttpSession): UserDTO? {
        log.info("showModify()")

        val email = session.getAttribute("email") as String
        val userDTO = userDao.showModify(email)

        return userDTO
    }

    @Throws(Exception::class)
    fun modify(files: List<MultipartFile?>?, userDTO: UserDTO, session: HttpSession) {
        log.info("modify()")
        var upFile: String? = null
        if (!files.isNullOrEmpty()) {
            upFile = files[0]!!.originalFilename
        }
        if (!upFile.isNullOrEmpty() && upFile.isNotBlank()) {
            fileUpload(files, session, userDTO)
        } else {
            userDTO.image = "noimage.png"
        }

        userDao.modify(userDTO)
    }

    @Throws(Exception::class)
    private fun fileUpload(
        files: List<MultipartFile?>?,
        session: HttpSession,
        userDTO: UserDTO
    ) {
        log.info("fileUpload()")
        var image: String? = null
        var original: String? = null

        var realPath = "C:/Users/dbals/git/bookrep-kotlin/src/main/resources/static/images/"
        log.info(realPath)
        val folder: File = File(realPath)
        if (!folder.isDirectory()) {
            folder.mkdir()
        }

        val mf = files!![0]
        original = mf!!.originalFilename

        image = System.currentTimeMillis()
            .toString() + original!!.substring(original!!.lastIndexOf("."))

        val file: File = File(realPath + image)

        mf.transferTo(file)
        userDTO.image = image
    }

    fun resign(session: HttpSession) {
        log.info("resign()")

        val email = session.getAttribute("email") as String

        userDao.resign(email)

        session.invalidate()
    }
}