package study.tipsyboy.boardApiProject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.tipsyboy.boardApiProject.exception.dto.ExceptionResponse;
import study.tipsyboy.boardApiProject.exception.dto.GeneralExceptionResponse;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GlobalBaseException.class)
    public ResponseEntity<ExceptionResponse> handlerException(GlobalBaseException e) {

        log.error("[EXCEPTION: BASE EXCEPTION]");

        ExceptionType exceptionType = e.getExceptionType();
        GeneralExceptionResponse exceptionResponse = GeneralExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(exceptionType.status().value())
                .message(exceptionType.exceptionMessage())
                .build();

        return new ResponseEntity<>(exceptionResponse, exceptionType.status());
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handlerException(Exception e) {

        log.error("[EXCEPTION: OTHER EXCEPTION]: {}", e.getMessage());

        GeneralExceptionResponse exceptionResponse = GeneralExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
