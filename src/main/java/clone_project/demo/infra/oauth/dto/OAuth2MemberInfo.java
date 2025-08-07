package clone_project.demo.infra.oauth.dto;

import clone_project.demo.domain.member.entity.Member;
import clone_project.demo.response.error.ErrorCode;
import clone_project.demo.response.exception.CustomException;
import lombok.Builder;

import java.util.Map;

@Builder
public record OAuth2MemberInfo (
    String name,
    String email,
    String profile
) {
    public static OAuth2MemberInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) {
            case "google" -> ofGoogle(attributes);
//            case "kakao" -> ofKakao(attributes);
            default -> throw new CustomException(ErrorCode.ILLEGAL_REGISTRATION_ID);
        };
    }

    private static OAuth2MemberInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2MemberInfo.builder()
                .name((String)attributes.get("name"))
                .email((String)attributes.get("email"))
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .role("ROLE_MEMBER")
                .build(); // password null
    }
}
