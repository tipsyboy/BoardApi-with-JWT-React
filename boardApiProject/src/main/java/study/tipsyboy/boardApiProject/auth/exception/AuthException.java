package study.tipsyboy.boardApiProject.auth.exception;

import study.tipsyboy.boardApiProject.security.jwt.exception.JwtExceptionType;

public class AuthException extends RuntimeException {
    public AuthException(AuthExceptionType authExceptionType) {
        super(authExceptionType.getMessage());
    }

    public AuthException(JwtExceptionType jwtExceptionType) {
        super(jwtExceptionType.getMessage());
    }
}
