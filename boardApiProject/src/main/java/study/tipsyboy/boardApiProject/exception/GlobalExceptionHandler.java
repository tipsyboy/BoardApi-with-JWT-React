package study.tipsyboy.boardApiProject.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import study.tipsyboy.boardApiProject.exception.dto.ExceptionResponse;
import study.tipsyboy.boardApiProject.exception.dto.GeneralExceptionResponse;
import study.tipsyboy.boardApiProject.exception.dto.ValidationExceptionResponse;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        log.info("exception: ", e);

        GeneralExceptionResponse exceptionResponse = GeneralExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(e.getMessage())
                .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionResponse> handlerValidationExceptions(MethodArgumentNotValidException e) {

        log.error("[EXCEPTION: VALIDATION FAILED]");

        List<FieldError> fieldErrors = e.getFieldErrors();
        Map<String, String> exceptionMessages = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
//            log.info("error={}", fieldError);
            exceptionMessages.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        ValidationExceptionResponse exceptionResponse = ValidationExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .validationExMessages(exceptionMessages)
                .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


    // TODO: loadByUsername() 에서의 예외처리가 의도한대로 발생하지 않고, AuthenticationException 발생
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ExceptionResponse> handlerException(AuthenticationException e) {

        log.error("[EXCEPTION: AUTHENTICATION FAILED]");

        GeneralExceptionResponse exceptionResponse = GeneralExceptionResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .message("로그인에 실패했습니다. 이메일과 비밀번호를 확인해 주세요.")
                .build();

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(AuthException.class)
//    public ResponseEntity<ExceptionResponse> handlerException(AuthException e) {
//
//        log.error("[EXCEPTION: AUTH EXCEPTION]");
//
//        AuthExceptionType exceptionType = (AuthExceptionType) e.getExceptionType();
//        Map<String, String> exceptionMessages = new HashMap<>();
//        if (exceptionType.getMessage().equals(AuthExceptionType.DUPLICATE_NICKNAME.getMessage())) {
//            exceptionMessages.put("nickname", AuthExceptionType.DUPLICATE_NICKNAME.getMessage());
//        } else if (exceptionType.getMessage().equals(AuthExceptionType.DUPLICATE_EMAIL.getMessage())) {
//            exceptionMessages.put("email", AuthExceptionType.DUPLICATE_EMAIL.getMessage());
//        }
//
//        ValidationExceptionResponse exceptionResponse = ValidationExceptionResponse.builder()
//                .timestamp(LocalDateTime.now())
//                .status(exceptionType.getHttpStatus().value())
//                .validationExMessages(exceptionMessages)
//                .build();
//
//        return new ResponseEntity<>(exceptionResponse, exceptionType.status());
//    }
}
