package study.tipsyboy.boardApiProject.posts.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import study.tipsyboy.boardApiProject.exception.ExceptionType;

@Getter
public enum PostsExceptionType implements ExceptionType {

    //
    NOT_FOUND_POSTS(HttpStatus.NOT_FOUND,"게시글이 존재하지 않습니다."),

    //
    BAD_REQUEST_AUTHORIZED(HttpStatus.UNAUTHORIZED, "권한이 없는 요청입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    PostsExceptionType(HttpStatus httpStatus, String message) {
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
