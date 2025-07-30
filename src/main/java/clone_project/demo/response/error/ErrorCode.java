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
    MEMBER_NOT_MATCH(HttpStatus.BAD_REQUEST, "ACCOUNT-003", "회원 정보가 일치하지 않습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
