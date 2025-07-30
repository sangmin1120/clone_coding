package clone_project.demo.domain.member.controller;

import clone_project.demo.domain.member.dto.MemberDto;
import clone_project.demo.domain.member.service.MemberService;
import clone_project.demo.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ApiResponse<Void> signup(@RequestBody MemberDto.Signup signupDto) {
        memberService.signup(signupDto);

        return ApiResponse.ok();
    }

    @PostMapping("/login")
    public ApiResponse<Void> login(@RequestBody MemberDto.Login loginDto) {
        memberService.login(loginDto);

        return ApiResponse.ok();
    }
}
