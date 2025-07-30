package clone_project.demo.response.exception;

import clone_project.demo.response.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    ErrorCode errorCode;
}
