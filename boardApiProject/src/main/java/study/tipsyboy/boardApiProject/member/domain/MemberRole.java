package study.tipsyboy.boardApiProject.member.domain;

import lombok.Getter;

@Getter
public enum MemberRole {

    ADMIN("ROLE_ADMIN", "관리자"),
    MEMBER("ROLE_MEMBER", "일반 사용자");

    private final String key;
    private final String role;

    MemberRole(String key, String role) {
        this.key = key;
        this.role = role;
    }
}
