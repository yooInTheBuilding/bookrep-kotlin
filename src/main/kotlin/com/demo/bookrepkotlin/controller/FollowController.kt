package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.dto.UserDTO
import com.demo.bookrepkotlin.service.FollowService
import com.demo.bookrepkotlin.util.MainUtil
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*


@Controller
class FollowController @Autowired constructor(val followService: FollowService){
    private val log = org.slf4j.LoggerFactory.getLogger(FollowController::class.java)

    @GetMapping("following/{email}")
    fun showFollowing(@PathVariable email: String?,
                      model: Model,
                      @RequestParam(required = false) pageNum: Int?): String {
        log.info("showFollowing()")

        val followingList: List<String?> = followService.getFollowingByEmail(email)!!
        var currentPage: Int? = 1
        if (pageNum != null) {
            currentPage = pageNum
        }
        val currentFollowingList = MainUtil.setPagingFollow(followingList, currentPage!!)

        val currentFollowingUserList: MutableList<UserDTO> = ArrayList()
        for (userEmail in currentFollowingList) {
            currentFollowingUserList.add(followService.getUserByEmail(userEmail)!!)
        }

        model.addAttribute("followingList", currentFollowingUserList)
        model.addAttribute("currentPageNum", currentPage)
        model.addAttribute("totalFollowingSize", followingList.size)
        model.addAttribute("email", email)
        return "following"
    }

    @GetMapping("follower/{email}")
    fun showFollower(@PathVariable email: String?,
                     model: Model,
                     @RequestParam(required = false) pageNum: Int?): String {
        log.info("showFollower()")

        val followerList: List<String?> = followService.getFollowerByEmail(email)!!
        var currentPage: Int? = 1
        if (pageNum != null) {
            currentPage = pageNum
        }
        val currentFollowerList = MainUtil.setPagingFollow(followerList, currentPage!!)

        val currentFollowerUserList: MutableList<UserDTO> = ArrayList()
        for (userEmail in currentFollowerList) {
            currentFollowerUserList.add(followService.getUserByEmail(userEmail)!!)
        }

        model.addAttribute("followerList", currentFollowerUserList)
        model.addAttribute("currentPageNum", currentPage)
        model.addAttribute("totalFollowerSize", followerList.size)
        model.addAttribute("email", email)

        return "follower"
    }

    @PostMapping("/follow")
    @ResponseBody
    fun follow(@RequestParam("email") followerEmail: String, session: HttpSession): String {
        log.info("follow()")

        val followeeEmail = session.getAttribute("email") as String

        followService.follow(followerEmail, followeeEmail)

        return "redirect:feed/$followerEmail"
    }

    @PostMapping("/unfollow")
    @ResponseBody
    fun unfollow(@RequestParam("email") followerEmail: String, session: HttpSession): String {
        log.info("unfollow()")

        val followeeEmail = session.getAttribute("email") as String

        followService.unfollow(followerEmail, followeeEmail)

        return "redirect:feed/$followerEmail"
    }
}