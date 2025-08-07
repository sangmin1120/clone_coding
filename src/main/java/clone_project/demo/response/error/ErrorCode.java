package clone_project.demo.response.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * member
     */
    MEMBER_NOT_FOUNT(HttpStatus.NOT_FOUND, "ACCOUNT-001", "사용자를 찾을 수 없습니다."),
    MEMBER_ALREADY_VALID(HttpStatus.BAD_REQUEST, "ACCOUNT-002", "이미 존재하는 사용자 입니다."),
    MEMBER_NOT_MATCH(HttpStatus.BAD_REQUEST, "ACCOUNT-003", "회원 정보가 일치하지 않습니다."),

    /**
     * TOKEN
     */
    TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "TOKEN-001","권한 정보가 없는 토큰입니다."),
    TOKEN_NOT_EXIST(HttpStatus.BAD_REQUEST, "TOKEN-002", "토큰이 존재하지 않습니다."),
    TOKEN_INVALID(HttpStatus.NOT_FOUND, "TOKEN-003", "토큰 정보가 잘못 됐습니다."),
    TOKEN_EXPIRATION(HttpStatus.BAD_REQUEST, "TOKEN-004","토큰이 만료되었습니다."),

    /**
     * OAuth2
     */
    ILLEGAL_REGISTRATION_ID(HttpStatus.BAD_REQUEST, "OAUTH2-001", "OAUTH2 registrationId 오류");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
