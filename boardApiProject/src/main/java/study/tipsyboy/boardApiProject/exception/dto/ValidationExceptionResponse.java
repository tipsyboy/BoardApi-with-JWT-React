package study.tipsyboy.boardApiProject.exception.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Getter
@SuperBuilder
public class ValidationExceptionResponse extends ExceptionResponse {

    private Map<String, String> validationExMessages;
}
