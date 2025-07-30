package clone_project.demo.domain.member.controller;

import clone_project.demo.domain.member.entity.Member;
import clone_project.demo.domain.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * 회원 정보, 백엔드 처리가 필요하는 컨트롤러
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
        log.info("[memberController] login()");

        Member member = memberService.login(request);
        if (member != null) {
            // 로그인 성공, 쿠키 등록
            Cookie cookie = new Cookie("accountId", member.getAccountId());
            cookie.setDomain("localhost");
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24 * 7); // 1주일 간 저장
            cookie.setSecure(true);
            response.addCookie(cookie);
            //model 등록
            model.addAttribute("member", member);
            return "redirect:/";
        }

        return "auth/login";
    }

    @PostMapping("/signup")
    public String signup(HttpServletRequest request, Model model) {
        log.info("[memberController] signup()");

        memberService.signup(request);
        return "redirect:/login";
    }
}
