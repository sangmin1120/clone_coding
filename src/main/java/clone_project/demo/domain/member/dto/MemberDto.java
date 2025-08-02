package clone_project.demo.domain.member.dto;

import clone_project.demo.domain.member.entity.Member;
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
        private String email;
        private String password;
        private MemberRole role;

        public Member toEntity() {
            return Member.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .role(MemberRole.MEMBER.toString())
                    .build();
        }
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login {
        private String email;
        private String password;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class InfoResponse {
        private String name;
        private String email;
    }
}
