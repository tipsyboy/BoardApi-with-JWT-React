package study.tipsyboy.boardApiProject.reply.exception;

public class ReplyException extends RuntimeException {

    public ReplyException(ReplyExceptionType replyExceptionType) {
        super(replyExceptionType.getMessage());
    }
}
