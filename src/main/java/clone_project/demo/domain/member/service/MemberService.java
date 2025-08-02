package clone_project.demo.domain.member.service;

import clone_project.demo.domain.member.dto.MemberDto;
import clone_project.demo.domain.member.entity.Member;
import clone_project.demo.domain.member.entity.MemberRole;
import clone_project.demo.domain.member.mapper.MemberMapper;
import clone_project.demo.infra.jwt.JwtTokenProvider;
import clone_project.demo.infra.jwt.dto.JwtToken;
import clone_project.demo.response.error.ErrorCode;
import clone_project.demo.response.exception.CustomException;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    // 회원 가입 로직 form
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
        memberMapper.save(signupDto.toEntity());
    }
    // api 회원가입 로직
    public void signup(MemberDto.Signup signupDto) {
        log.info("[memberService] signup()");

        // 로직
        if (memberMapper.findByEmail(signupDto.getEmail()) != null) {
            // 이메일 중복
            throw new CustomException(ErrorCode.MEMBER_ALREADY_VALID);
        }

        // 저장
        signupDto.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        memberMapper.save(signupDto.toEntity());
    }

    // 로그인 로직 - 동일 아이디가 있을 때, 회원가입 실패 처리 form
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
    // api 회원가입 로직
    public JwtToken login(MemberDto.Login loginDto) {
        log.info("[memberService] login()");

        Member member = memberMapper.findByEmail(loginDto.getEmail());
        if (member == null) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUNT);
        }

        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.MEMBER_NOT_MATCH);
        }
        // JWT 구현할 거면 여기서 토큰 값을 반환해 주어야 됨
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        return jwtTokenProvider.generateToken(authentication);
    }

    public MemberDto.InfoResponse info(String accessToken) {
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        String email = authentication.getName();
        Member member = memberMapper.findByEmail(email);

        if (member == null) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUNT);
        }

        return MemberDto.InfoResponse.builder()
                .name(member.getName())
                .email(email)
                .build();
    }
}
