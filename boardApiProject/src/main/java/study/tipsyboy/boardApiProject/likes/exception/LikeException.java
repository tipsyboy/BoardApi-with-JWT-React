package study.tipsyboy.boardApiProject.likes.exception;

import study.tipsyboy.boardApiProject.exception.ExceptionType;
import study.tipsyboy.boardApiProject.exception.GlobalBaseException;

public class LikeException extends GlobalBaseException {

    public LikeException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
