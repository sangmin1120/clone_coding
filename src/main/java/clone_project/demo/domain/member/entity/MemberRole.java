package clone_project.demo.domain.member.entity;

import lombok.Getter;

@Getter
public enum MemberRole {
    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER");

    private final String role;

    MemberRole(String roleAdmin) {
        this.role = roleAdmin;
    }

}
