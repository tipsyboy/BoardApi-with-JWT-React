package study.tipsyboy.boardApiProject.security.jwt.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class JwtExceptionResponseDto {

    private String path;
    private Integer statusCode;
    private String exceptionCode;
    private String message;

}
