package clone_project.demo.response;

import clone_project.demo.response.error.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private String code;
    private T data;
    private String message;

    public static ApiResponse<Void> ok() {
        return new ApiResponse<Void>("200", null, "요청 성공");
    }

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>("200", data, "요청 성공");
    }

    public static ApiResponse<String> fail(ErrorCode e) {
        return new ApiResponse<>(e.getCode(), e.getMessage(), null);
    }
}
