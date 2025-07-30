package clone_project.demo.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * member
     */
    MEMBER_NOT_FOUNT(HttpStatus.NOT_FOUND, "ACCOUNT-001", "사용자를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
