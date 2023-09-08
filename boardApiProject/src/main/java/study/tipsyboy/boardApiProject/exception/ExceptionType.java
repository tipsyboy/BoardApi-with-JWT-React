package study.tipsyboy.boardApiProject.exception;

import org.springframework.http.HttpStatus;

public interface ExceptionType {

    HttpStatus status();
    String exceptionMessage();

}
