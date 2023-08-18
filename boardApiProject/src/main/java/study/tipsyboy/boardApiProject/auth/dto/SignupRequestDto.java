package study.tipsyboy.boardApiProject.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignupRequestDto {

    private String email;
    private String password;
    private String nickname;

}
