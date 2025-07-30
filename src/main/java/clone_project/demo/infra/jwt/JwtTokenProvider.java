package clone_project.demo.infra.jwt;

import clone_project.demo.infra.jwt.dto.JwtToken;
import clone_project.demo.infra.jwt.repository.TokenRedis;
import clone_project.demo.infra.jwt.repository.TokenRedisRepository;
import clone_project.demo.response.error.ErrorCode;
import clone_project.demo.response.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private final Key key;
    private final TokenRedisRepository tokenRedisRepository;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, TokenRedisRepository tokenRedisRepository) {
        this.tokenRedisRepository = tokenRedisRepository;
        byte[] KeyBytes = Decoders.BASE64.decode(secretKey); // Base64로 인코딩된 SecretKey 디코딩
        this.key = Keys.hmacShaKeyFor(KeyBytes); // secret key 이용하여 key 객체 생성
    }

    // 유저 정보를 이용하여 AccessToken, RefreshToken을 생성하는 메서드
    public JwtToken generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date issuedAt = new Date(); //토큰 발급 시각

        //Access Token 생성
        String accessToken = Jwts.builder()
                .setHeader(createHeader()) // 헤더 부분 설정
                .setSubject("accessToken") // 토큰 주제 설정
                .claim("iss", "off") //토큰 발급자 설정
                .claim("email", authentication.getName()) // 토큰 대상자 설정
                .claim("auth", authorities) // 사용자 권한 설정
                .setExpiration(new Date(now + 60000 * 60 * 30)) // 30 분 설정
                .setIssuedAt(issuedAt) // 토큰 발급 시각 설정
                .signWith(key, SignatureAlgorithm.HS256) // 서명 알고리즘 설정
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setHeader(createHeader())
                .setSubject("refreshToken")
                .claim("iss", "off")
                .claim("email", authentication.getName())
                .claim("auth", authorities)
                .claim("add", "ref") // 추가 정보
                .setExpiration(new Date(now + 6000 * 60 * 60 * 24 * 7)) // 7일 설정
                .setIssuedAt(issuedAt)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer") // 토큰 타입 설정
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내 Authentication 객체를 생성하는 메서드
    public Authentication getAuthentication(String token) {
        // jwt 복호화
        Claims claims = parseClaims(token);

        if (claims.get("auth") == null) {
            throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
        }

        // 클레임에서 권한 가져오기
        List<SimpleGrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        //UserDetails 객체를 만들어서 Authentication return
        //UserDetails: interface, User: UserDetails를 구현한 class
        UserDetails principal = new User((String) claims.get("email"), "", authorities);

        // UsernamePasswordAuthenticationToken 객체 반환
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    // JWT 토큰 유효성 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SignatureException | MalformedJwtException e) {
            // 토큰이 잘못된 경우
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        } catch (ExpiredJwtException e) {
            // 토큰이 만료된 경우
            throw new CustomException(ErrorCode.TOKEN_NOT_EXIST);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            // 지원하지 않는 토큰
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        } catch (Exception e) {
            // 그 외 예외 처리
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        }
    }

    //RefreshToken을 이용해 AccessToken 재발급하는 메서드
    public JwtToken refreshToken(String refreshToken) {
        try {
            // RefreshToken 복호화
            Authentication authentication = getAuthentication(refreshToken);

            TokenRedis tokenRedis = tokenRedisRepository.findById(authentication.getName()).orElseThrow();

            JwtToken refreshGetToken = null;
            // Redis에 저장된 refreshToken과 요청된 refreshToken이 일치할 경우
            if (refreshToken.equals(tokenRedis.getRefreshToken())) {
                refreshGetToken = generateToken(authentication); // 토큰 재발급

                saveToken(refreshGetToken, authentication); // Redis에 새로운 refreshToken 정보 저장
                return refreshGetToken; // 새로운 토큰 반환
            } else {
                log.warn("does not exist Token");
                // redis에 refreshToken 없음
                throw new CustomException(ErrorCode.TOKEN_NOT_FOUND);
            }
        } catch (NullPointerException e) {
            log.warn("token is null");
            throw new CustomException(ErrorCode.TOKEN_NOT_EXIST);
        } catch (NoSuchElementException e) {
            log.warn("no such token");
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        }
    }

    private void saveToken(JwtToken refreshGetToken, Authentication authentication) {
        TokenRedis tokenRedis = new TokenRedis(authentication.getName(), refreshGetToken.getRefreshToken());
        tokenRedisRepository.save(tokenRedis);
    }

    //JWT 토큰을 파싱하여 클레임 정보를 반환하는 메서드
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims(); // 마료된 토큰의 경우 클레임 정보 반환
        } catch (Exception e) {
            throw new CustomException(ErrorCode.TOKEN_INVALID);
        }
    }

    // JWT 토큰의 header 정보를 생성하는 메서드
    private Map<String,Object> createHeader() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");
        return headers;
    }
}
