package study.tipsyboy.boardApiProject.auth.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.auth.domain.RefreshToken;
import study.tipsyboy.boardApiProject.auth.domain.RefreshTokenRepository;
import study.tipsyboy.boardApiProject.auth.dto.LoginRequestDto;
import study.tipsyboy.boardApiProject.auth.dto.LoginResponseDto;
import study.tipsyboy.boardApiProject.auth.dto.SignupRequestDto;
import study.tipsyboy.boardApiProject.auth.exception.AuthException;
import study.tipsyboy.boardApiProject.auth.exception.AuthExceptionType;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.member.domain.MemberRepository;
import study.tipsyboy.boardApiProject.member.domain.MemberRole;
import study.tipsyboy.boardApiProject.security.jwt.util.TokenProvider;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("회원가입을 한다.")
    public void signupMember() throws Exception {
        // given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email("test@test.com")
                .password("password123")
                .nickname("tester")
                .build();
        Long memberId = authService.signup(signupRequestDto);

        // when
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new AuthException(AuthExceptionType.NOT_FOUND_EMAIL));

        // then
        assertEquals(memberId, findMember.getId());
        assertEquals("test@test.com", findMember.getEmail());
        assertTrue(passwordEncoder.matches("password123", findMember.getPassword()));
        assertEquals("tester", findMember.getNickname());
    }

    @Test
    @DisplayName("회원가입을 실패한다. - 이메일 중복")
    public void failSignupDuplicateEmail() throws Exception {
        // given
        Member member = Member.builder()
                .email("test@test.com")
                .password("password123")
                .nickname("tester1")
                .memberRole(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);

        // expected
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email("test@test.com")
                .password("password123")
                .nickname("tester2")
                .build();

        assertThrows(AuthException.class, ()
                -> authService.signup(signupRequestDto));
    }

    @Test
    @DisplayName("회원가입을 실패한다. - 닉네임 중복")
    public void failSignupDuplicateNickName() throws Exception {
        // given
        Member member = Member.builder()
                .email("test1@test.com")
                .password("password123")
                .nickname("tester1")
                .memberRole(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);

        // expected
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email("test2@test.com")
                .password("password123")
                .nickname("tester1")
                .build();

        assertThrows(AuthException.class, ()
                -> authService.signup(signupRequestDto));
    }
    
    @Test
    @DisplayName("로그인을 한다.")
    public void login() throws Exception {
        // given
        Member member = Member.builder()
                .email("test@test.com")
                .password(passwordEncoder.encode("password123"))
                .nickname("tester")
                .memberRole(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);

        // when
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@test.com")
                .password("password123")
                .build();
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);

        // then
        Authentication authentication = tokenProvider.getAuthentication(loginResponseDto.getAccessToken());

        assertEquals("test@test.com", authentication.getName());
        assertTrue(authentication.isAuthenticated());

        assertTrue(tokenProvider.isValidToken(loginResponseDto.getAccessToken()));
        RefreshToken ogRefreshToken = refreshTokenRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new AuthException(AuthExceptionType.NOT_FOUND_EMAIL));
        assertEquals(loginResponseDto.getRefreshToken(), ogRefreshToken.getToken());
    }
    
    @Test
    @DisplayName("로그인을 실패한다")
    public void failLogin() throws Exception {
        // given
        Member member = Member.builder()
                .email("test@test.com")
                .password(passwordEncoder.encode("password123"))
                .nickname("tester")
                .memberRole(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);

        // when
        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@test.com")
                .password("password1234")
                .build();

        // then TODO: BadCredentialsException 다루기
        assertThrows(BadCredentialsException.class, ()
                -> authService.login(loginRequestDto));
    }
    
}