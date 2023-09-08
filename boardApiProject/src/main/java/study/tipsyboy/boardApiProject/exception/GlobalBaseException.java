package study.tipsyboy.boardApiProject.exception;

import lombok.Getter;

@Getter
public class GlobalBaseException extends RuntimeException {

    private final ExceptionType exceptionType;

    public GlobalBaseException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }
}
