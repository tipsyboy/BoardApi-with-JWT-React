package study.tipsyboy.boardApiProject.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class LogoutRequestDto {

    private String refreshToken;

}
