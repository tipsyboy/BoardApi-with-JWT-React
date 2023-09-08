package study.tipsyboy.boardApiProject.auth.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.tipsyboy.boardApiProject.exception.ExceptionType;

@Getter
public enum AuthExceptionType implements ExceptionType {

    DUPLICATE_EMAIL(HttpStatus.CONFLICT ,"이미 등록되어 있는 이메일입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 등록되어 있는 닉네임입니다."),

    NOT_FOUND_EMAIL(HttpStatus.NOT_FOUND, "등록되지 않은 사용자입니다."),
    LOGOUT_EMAIL(HttpStatus.NOT_FOUND,"로그아웃된 사용자입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    AuthExceptionType(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public HttpStatus status() {
        return this.httpStatus;
    }

    @Override
    public String exceptionMessage() {
        return this.message;
    }
}
