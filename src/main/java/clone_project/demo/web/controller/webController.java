package clone_project.demo.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 웹 페이지 반환하는 컨트롤러
 */
@Controller
@Slf4j
public class webController {

    //로그인 페이지 반환
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    //회원 가입 페이지
    @GetMapping("/signup")
    public String signup() {
        return "auth/signup";
    }
}
