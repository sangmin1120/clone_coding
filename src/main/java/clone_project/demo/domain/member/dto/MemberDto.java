package clone_project.demo.domain.member.dto;

import clone_project.demo.domain.member.entity.MemberRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class MemberDto {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Signup {
        private String name;
        private String accountId;
        private String password;
        private MemberRole role;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        private String accountId;
        private String password;
    }
}
