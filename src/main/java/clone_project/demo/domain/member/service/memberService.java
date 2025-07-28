package clone_project.demo.domain.member.service;

import clone_project.demo.domain.member.dto.MemberDto;
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

    public MemberDto signup(HttpServletRequest request) {
        log.info("[memberService] signup()");

        String name = request.getParameter("name");
        String accountId = request.getParameter("accountId");
        String password = passwordEncoder.encode(request.getParameter("password"));
        MemberDto memberDto = new MemberDto(name, accountId, password);

        // 회원 데이터 검증

        // 데이터 저장
        memberMapper.save(memberDto);

        return memberDto;
    }
}
