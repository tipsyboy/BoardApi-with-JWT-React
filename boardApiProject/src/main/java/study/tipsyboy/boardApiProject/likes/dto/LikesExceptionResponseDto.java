package study.tipsyboy.boardApiProject.likes.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LikesExceptionResponseDto {

    private Integer errorCode;
    private String message;

}
