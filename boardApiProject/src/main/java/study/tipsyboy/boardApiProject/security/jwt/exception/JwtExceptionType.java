package study.tipsyboy.boardApiProject.security.jwt.exception;

import lombok.Getter;

@Getter
public enum JwtExceptionType {

    UNKNOWN_ERROR("UNKNOWN_ERROR", "UNKNOWN_ERROR"),
    NOT_FOUND_TOKEN("NOT_FOUND_TOKEN", "토큰이 존재하지 않습니다."),
    INVALID_TOKEN("INVALID_TOKEN", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN("EXPIRED_TOKEN", "기간이 만료된 토큰입니다."),
    UNSUPPORTED_TOKEN("UNSUPPORTED_TOKEN", "지원하지 않는 토큰입니다.");


    private final String code;
    private final String message;

    JwtExceptionType(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
