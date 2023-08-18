package study.tipsyboy.boardApiProject.auth.exception;

public class AuthException extends RuntimeException {
    public AuthException(AuthExceptionType authExceptionType) {
        super(authExceptionType.getMessage());
    }
}
