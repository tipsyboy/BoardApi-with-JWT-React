package study.tipsyboy.boardApiProject.auth.exception;

import study.tipsyboy.boardApiProject.exception.ExceptionType;
import study.tipsyboy.boardApiProject.exception.GlobalBaseException;
import study.tipsyboy.boardApiProject.security.jwt.exception.JwtExceptionType;

public class AuthException extends GlobalBaseException {

    public AuthException(ExceptionType exceptionType) {
        super(exceptionType);
    }

//    public AuthException(AuthExceptionType authExceptionType) {
//        super(authExceptionType.getMessage());
//    }
//
//    public AuthException(JwtExceptionType jwtExceptionType) {
//        super(jwtExceptionType.getMessage());
//    }
}
