package study.tipsyboy.boardApiProject.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.auth.domain.RefreshToken;
import study.tipsyboy.boardApiProject.auth.domain.RefreshTokenRepository;
import study.tipsyboy.boardApiProject.auth.dto.LoginRequestDto;
import study.tipsyboy.boardApiProject.auth.dto.LoginResponseDto;
import study.tipsyboy.boardApiProject.auth.dto.SignupRequestDto;
import study.tipsyboy.boardApiProject.auth.dto.TokenReissueRequestDto;
import study.tipsyboy.boardApiProject.auth.exception.AuthException;
import study.tipsyboy.boardApiProject.auth.exception.AuthExceptionType;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.member.domain.MemberRepository;
import study.tipsyboy.boardApiProject.member.domain.MemberRole;
import study.tipsyboy.boardApiProject.security.jwt.util.TokenProvider;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public Long signup(SignupRequestDto requestDto) {

        if (memberRepository.existsByEmail(requestDto.getEmail())) {
            throw new AuthException(AuthExceptionType.DUPLICATE_EMAIL);
        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .nickname(requestDto.getNickname())
                .memberRole(MemberRole.MEMBER)
                .build();

        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);

        String accessToken = tokenProvider.createAccessToken(authentication);
        String refreshToken = tokenProvider.createRefreshToken(authentication);

        refreshTokenRepository.save(new RefreshToken(requestDto.getEmail(), refreshToken));

        return new LoginResponseDto(accessToken, refreshToken);
    }

    @Transactional
    public LoginResponseDto reissue(TokenReissueRequestDto requestDto) {
        Authentication authentication = tokenProvider.getAuthentication(requestDto.getAccessToken());
        RefreshToken refreshToken = refreshTokenRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new AuthException(AuthExceptionType.LOGOUT_EMAIL));

        if (!refreshToken.getToken().equals(requestDto.getRefreshToken())) {
            throw new RuntimeException("Token 정보가 일치하지 않습니다.");
        }

        String accessToken = tokenProvider.createAccessToken(authentication);
        String newRefreshToken = tokenProvider.createRefreshToken(authentication);
        refreshTokenRepository.save(refreshToken.updateToken(newRefreshToken));

        return new LoginResponseDto(accessToken, newRefreshToken);
    }

    @Transactional
    public void deleteRefreshToken(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken)
                .ifPresent(token -> refreshTokenRepository.delete(token));
    }
}
