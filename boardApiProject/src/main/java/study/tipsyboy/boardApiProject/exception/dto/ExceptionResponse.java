package study.tipsyboy.boardApiProject.exception.dto;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public abstract class ExceptionResponse {

    private LocalDateTime timestamp;
    private Integer status;
}
