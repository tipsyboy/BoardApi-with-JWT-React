package study.tipsyboy.boardApiProject.reply.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.tipsyboy.boardApiProject.exception.ExceptionType;

@Getter
public enum ReplyExceptionType implements ExceptionType {

    //
    REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),

    //
    BAD_REQUEST_AUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없는 요청입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ReplyExceptionType(HttpStatus httpStatus, String message) {
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
