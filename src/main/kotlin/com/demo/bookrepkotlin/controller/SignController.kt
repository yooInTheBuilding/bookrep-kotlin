package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.dto.UserDTO
import com.demo.bookrepkotlin.service.SignService
import jakarta.servlet.http.HttpSession
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes


@Controller
class SignController @Autowired constructor(val signService: SignService){
    private val log = LoggerFactory.getLogger(this.javaClass)

    // 로그인 이동
    @GetMapping("sign-in")
    fun showSignIn(): String {
        log.info("로그인 화면 이동")
        return "signIn"
    }

    // 로그인 로직
    @PostMapping("sign-in")
    fun signIn(session: HttpSession?, @RequestParam email: String?, @RequestParam password: String?): String {
        log.info("email:{}, pw:{}", email, password)

        val view: String = signService.signIn(session!!, email, password)
        log.info(view)
        return view
    }

    // 로그아웃 로직
    @GetMapping("sign-out")
    fun signOut(session: HttpSession): String {
        session.invalidate()
        log.info("로그아웃")
        return "home1"
    }

    @GetMapping("sign-up")
    fun signUp(): String {
        log.info("signUp()")

        return "signUp"
    }

    @PostMapping("/emailCheck")
    @ResponseBody
    fun emailCheck(@RequestParam("email") email: String?): String {
        log.info("emailCheck()")

        val cnt: Int = signService.emailCheck(email)

        return cnt.toString()
    }

    @PostMapping("sign-up")
    fun applySignUp(
        @RequestParam("email") email: String?,
        @RequestParam("name") name: String?,
        @RequestParam("password") password: String?,
        rttr: RedirectAttributes
    ): String {
        log.info("applySignUp()")

        val arr: Array<String> = signService.applySignUp(email, name, password)

        rttr.addFlashAttribute("msg", arr[0])

        return arr[1]
    }

    @GetMapping("find-password")
    fun showPassFinder(): String {
        log.info("showPassFinder")

        return "findPassword"
    }

    @PostMapping(value = ["find-password"], produces = ["text/plain;charset=UTF-8"])
    @ResponseBody
    fun findPassword(@RequestParam("email") email: String?, @RequestParam("name") name: String?): String {
        log.info("findPassword")

        val result: String = signService.findPassword(email, name)

        return result
    }

    @GetMapping("update")
    fun showModify(session: HttpSession?, model: Model): String {
        log.info("showModify()")

        val userDTO: UserDTO = signService.showModify(session!!)!!
        model.addAttribute("user", userDTO)

        return "update"
    }

    @PostMapping("update")
    @Throws(Exception::class)
    fun modify(@RequestPart files: List<MultipartFile?>?, userDTO: UserDTO?, session: HttpSession?): String {
        log.info("modify()")

        signService.modify(files, userDTO!!, session!!)

        return "redirect:/"
    }

    @GetMapping("resign")
    fun resign(session: HttpSession?): String {
        log.info("resign()")

        signService.resign(session!!)

        return "redirect:/"
    }
}