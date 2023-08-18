package study.tipsyboy.boardApiProject.auth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.auth.dto.SignupRequestDto;
import study.tipsyboy.boardApiProject.auth.exception.AuthException;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.member.domain.MemberRepository;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired AuthService authService;
    @Autowired MemberRepository memberRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입 - 회원가입을 수행한다.")
    void signup() {
        // given
        String testEmail = "tester12@example.com";
        String testPassword = "test1234";
        String testNickname = "김인간";
        SignupRequestDto requestDto = new SignupRequestDto(testEmail, testPassword, testNickname);

        // when
        Long memberId = authService.signup(requestDto);
        Member findMember = memberRepository.findById(memberId).orElse(null);

        // then
        assertNotNull(findMember);
        assertThat(memberId).isEqualTo(findMember.getId());
        assertThat(testEmail).isEqualTo(findMember.getEmail());
        assertThat(testNickname).isEqualTo(findMember.getNickname());
        assertTrue(passwordEncoder.matches(testPassword, findMember.getPassword()));
    }

    @Test
    @DisplayName("회원가입 - 중복 이메일 검사")
    void signup_duplicateEmail() {
        // given
        String duplicateEmail = "tester12@example.com";
        String testPassword1 = "test1234";
        String testNickname1 = "김인간";
        SignupRequestDto requestDto1 = new SignupRequestDto(duplicateEmail, testPassword1, testNickname1);
        authService.signup(requestDto1);

        // when
        String testPassword2 = "test1234";
        String testNickname2 = "김인간";
        SignupRequestDto requestDto2 = new SignupRequestDto(duplicateEmail, testPassword2, testNickname2);

        // then
        assertThrows(AuthException.class,
                () -> authService.signup(requestDto2));
    }
}