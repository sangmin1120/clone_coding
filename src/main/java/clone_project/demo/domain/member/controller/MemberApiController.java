package clone_project.demo.domain.member.controller;

import clone_project.demo.domain.member.dto.MemberDto;
import clone_project.demo.domain.member.service.MemberService;
import clone_project.demo.infra.jwt.dto.JwtToken;
import clone_project.demo.infra.jwt.util.JwtUtil;
import clone_project.demo.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public ApiResponse<JwtToken> login(@RequestBody MemberDto.Login loginDto) {

        return ApiResponse.ok(memberService.login(loginDto));
    }

    @GetMapping("/info")
    public ApiResponse<MemberDto.InfoResponse> info(HttpServletRequest request) {

        return ApiResponse.ok(memberService.info(JwtUtil.getAccessToken(request)));
    }
}
