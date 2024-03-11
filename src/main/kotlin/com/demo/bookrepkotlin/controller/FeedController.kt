package com.demo.bookrepkotlin.controller

import com.demo.bookrepkotlin.service.FeedService
import com.demo.bookrepkotlin.service.FollowService
import jakarta.servlet.http.HttpSession
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam


@Controller
class FeedController @Autowired constructor(val feedService: FeedService, val followService: FollowService){
    private val log = org.slf4j.LoggerFactory.getLogger(this.javaClass)

    @GetMapping("feed/{userEmail}")
    fun showFeed(@PathVariable userEmail: String?,
                 session: HttpSession,
                 model: Model,
                 @RequestParam(required = false) pageNum: Int): String {
        // 세션에서 현재 로그인한 사용자 이메일 가져옴.

        val loggedInUserEmail = session.getAttribute("email") as String

        val isFollowing: Boolean = followService.isFollowing(userEmail, loggedInUserEmail)

        model.addAttribute("isFollowing", isFollowing)

        val name: String? = feedService.getNameByEmail(userEmail)

        model.addAttribute("name", name)


        // 매개변수인 userEmail을 사용해서 해당 유저의 독후감 정보를 가져옴.
        val sessionItems: List<Any?>? = feedService.getReportSummaryById(userEmail!!, loggedInUserEmail, pageNum)

        // 페이지 주인인 유저의 정보를 전달
        model.addAttribute("userEmail", userEmail)


        // 페이지 유저의 사진 정보 가져오는 메서드
        val userImage: String = feedService.getUserImage(userEmail)!!


        // 유저 이미지 전달
        model.addAttribute("userImage", userImage)

        // 독후감 정보 추가
        model.addAttribute("sessionItems", sessionItems)

        // 로그인한 사용자와 현재 보고 있는 페이지의 주인이 같은가 판별
        if (loggedInUserEmail != null && loggedInUserEmail == userEmail) {
            // 같은 경우 로그인한 사용자의 추가 정보를 모델에 추가
            model.addAttribute("isCurrentUser", true)
            log.info("로그인유저==페이지유저")
        } else {
            model.addAttribute("isCurrentUser", false)
            log.info("로그인유저!=페이지유저")
        }


        // 모델에 잘 담겼는지 중간 확인용
        log.info("model = $model")

        if (userEmail != null) {
            if (userEmail === "aaa@aaa.com") {
                log.info("확인용 임시 이메일입니다.")
            }
            // 팔로워 수 출력
            val followerCnt: Int = followService.getFollowerValueByEmail(userEmail)
            println(followerCnt)
            model.addAttribute("followerCnt", followerCnt)
            // 팔로잉 수 출력
            val followingCnt: Int = followService.getFollowingValueByEmail(userEmail)
            model.addAttribute("followingCnt", followingCnt)
            println(followingCnt)
            // 총 포스트 수 출력
            val reportValue: Int = feedService.getReportValueById(userEmail)
            model.addAttribute("reportValue", reportValue)
            println(reportValue)


            // 잘 담겼나 확인용
            log.info("model = $model")
        }

        return "feed"
    }
}