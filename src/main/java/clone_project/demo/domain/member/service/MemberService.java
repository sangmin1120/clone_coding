package clone_project.demo.domain.member.service;

import clone_project.demo.domain.member.dto.MemberDto;
import clone_project.demo.domain.member.entity.Member;
import clone_project.demo.domain.member.entity.MemberRole;
import clone_project.demo.domain.member.mapper.MemberMapper;
import clone_project.demo.response.error.ErrorCode;
import clone_project.demo.response.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입 로직
    public void signup(HttpServletRequest request) {
        log.info("[memberService] signup()");

        String name = request.getParameter("name");
        String accountId = request.getParameter("accountId");
        String password = passwordEncoder.encode(request.getParameter("password"));
        MemberDto.Signup signupDto = new MemberDto.Signup(name, accountId, password, MemberRole.MEMBER); // Member의 역할로 저장

        // 회원 데이터 검증
        if (memberMapper.findByEmail(accountId) != null) {
            throw new CustomException(ErrorCode.MEMBER_ALREADY_VALID);
        }

        // 데이터 저장
        memberMapper.save(signupDto);
    }
    public void signup(MemberDto.Signup signupDto) {
        log.info("[memberService] signup()");

        // 로직
        if (memberMapper.findByEmail(signupDto.getEmail()) != null) {
            throw new CustomException(ErrorCode.MEMBER_ALREADY_VALID);
        }

        // 저장
        memberMapper.save(signupDto);
    }

    // 로그인 로직 - 동일 아이디가 있을 때, 회원가입 실패 처리
    public Member login(HttpServletRequest request) {
        log.info("[memberService] login()");

        String name = request.getParameter("name");
        String accountId = request.getParameter("accountId");
        String password = request.getParameter("password");
        Member member = memberMapper.findByEmail(accountId);
        if (member == null) {
            // 회원 정보가 없음
            return null;
        }
        if (!passwordEncoder.matches(password, member.getPassword())) {
            // 비밀번호 실패 -> 에러처리 구현해야됨
        }

        return member;
    }
    public void login(MemberDto.Login loginDto) {
        log.info("[memberService] login()");

        Member member = memberMapper.findByEmail(loginDto.getAccountId());
        if (member == null) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUNT);
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_MATCH);
        }
        // JWT 구현할 거면 여기서 토큰 값을 반환해 주어야 됨
    }
}
