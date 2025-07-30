package clone_project.demo.response.error;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

@Data
@Builder
public class ErrorResponseEntity {

    private int status;
    private String name;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e) {
        return ResponseEntity
                .status(e.getHttpStatus()) // httpStatus 자체가 들어감
                .body(ErrorResponseEntity.builder()
                        .status(e.getHttpStatus().value()) // 200
                        .name(e.name())
                        .message(e.getMessage())
                        .build());
    }
}
