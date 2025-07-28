package clone_project.demo.domain.member.service;

import clone_project.demo.domain.member.dto.MemberDto;
import clone_project.demo.domain.member.entity.Member;
import clone_project.demo.domain.member.mapper.MemberMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class memberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입 로직
    public void signup(HttpServletRequest request) {
        log.info("[memberService] signup()");

        String name = request.getParameter("name");
        String accountId = request.getParameter("accountId");
        String password = passwordEncoder.encode(request.getParameter("password"));
        MemberDto memberDto = new MemberDto(name, accountId, password);

        // 회원 데이터 검증

        // 데이터 저장
        memberMapper.save(memberDto);
    }

    // 로그인 로직 - 동일 아이디가 있을 때, 회원가입 실패 처리
    public Member login(HttpServletRequest request) {
        log.info("[memberService] login()");

        String name = request.getParameter("name");
        String accountId = request.getParameter("accountId");
        String password = request.getParameter("password");
        Member member = memberMapper.findByAccountId(accountId);
        if (member == null) {
            // 회원 정보가 없음
            return null;
        }
        if (!passwordEncoder.matches(password, member.getPassword())) {
            // 비밀번호 실패 -> 에러처리 구현해야됨
        }

        return member;
    }
}
