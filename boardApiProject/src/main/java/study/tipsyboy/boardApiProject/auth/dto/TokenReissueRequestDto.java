package study.tipsyboy.boardApiProject.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TokenReissueRequestDto {

    private String accessToken;
    private String refreshToken;

}
