package study.tipsyboy.boardApiProject.auth.exception;

import study.tipsyboy.boardApiProject.exception.ExceptionType;
import study.tipsyboy.boardApiProject.exception.GlobalBaseException;

public class AuthException extends GlobalBaseException {
    public AuthException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
