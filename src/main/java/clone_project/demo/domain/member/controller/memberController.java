package clone_project.demo.domain.member.controller;

import clone_project.demo.domain.member.dto.MemberDto;
import clone_project.demo.domain.member.mapper.MemberMapper;
import clone_project.demo.domain.member.service.memberService;
import jakarta.servlet.http.HttpServletRequest;
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
public class memberController {

    private final memberService memberService;

    @PostMapping("/signup")
    public String login(HttpServletRequest request, Model model) {
        log.info("[memberController] signup()");

        MemberDto memberDto = memberService.signup(request);

        // 데이터 등록
        model.addAttribute("member",memberDto);
        // 로그인 페이지 호출
        return "test";
    }
}
