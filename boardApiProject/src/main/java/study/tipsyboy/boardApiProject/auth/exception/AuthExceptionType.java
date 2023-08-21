package study.tipsyboy.boardApiProject.auth.exception;

import lombok.Getter;

@Getter
public enum AuthExceptionType {
    DUPLICATE_EMAIL("이미 등록되어 있는 이메일입니다."),
    DUPLICATE_NICKNAME("이미 등록되어 있는 닉네임입니다."),
    NOT_EXISTS_EMAIL("등록되지 않은 이메일입니다.");

    private final String message;

    AuthExceptionType(String message) {
        this.message = message;
    }
}
