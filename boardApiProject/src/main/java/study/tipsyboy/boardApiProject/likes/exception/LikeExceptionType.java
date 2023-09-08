package study.tipsyboy.boardApiProject.likes.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.tipsyboy.boardApiProject.exception.ExceptionType;

@Getter
public enum LikeExceptionType implements ExceptionType {

    DUPLICATE_LIKES(HttpStatus.BAD_REQUEST, "이미 추천하였습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    LikeExceptionType(HttpStatus httpStatus, String message) {
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
