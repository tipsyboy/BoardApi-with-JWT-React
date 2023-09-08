package study.tipsyboy.boardApiProject.exception.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class GeneralExceptionResponse extends ExceptionResponse {

    private String message;
}
