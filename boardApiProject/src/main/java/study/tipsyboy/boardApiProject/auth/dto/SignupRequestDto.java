package study.tipsyboy.boardApiProject.auth.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SignupRequestDto {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 8, max = 50, message = "비밀번호가 너무 짧거나 깁니다. 8~50자 비밀번호를 사용해주세요.")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Length(min = 2, max = 20, message = "닉네임이 너무 짧거나 깁니다. 2~20자 닉네임을 사용해주세요.")
    @Pattern(regexp = "^[가-힣a-z0-9_-]{2,20}$", message = "닉네임이 올바르지 않습니다. 공백없이 한글, 영어 소문자, 숫자를 사용해주세요.")
    private String nickname;

}
