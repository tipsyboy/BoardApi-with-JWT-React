package study.tipsyboy.boardApiProject.reply.exception;

import lombok.Getter;

@Getter
public enum ReplyExceptionType {

    REPLY_NOT_FOUND("댓글을 찾을 수 없습니다."),
    BAD_REQUEST_AUTHORIZED("권한이 없는 요청입니다.");

    private final String message;

    ReplyExceptionType(String message) {
        this.message = message;
    }
}
