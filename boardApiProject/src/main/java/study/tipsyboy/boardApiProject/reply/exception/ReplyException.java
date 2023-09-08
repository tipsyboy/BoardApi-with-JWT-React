package study.tipsyboy.boardApiProject.reply.exception;

import study.tipsyboy.boardApiProject.exception.ExceptionType;
import study.tipsyboy.boardApiProject.exception.GlobalBaseException;

public class ReplyException extends GlobalBaseException {

    public ReplyException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
