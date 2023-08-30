package study.tipsyboy.boardApiProject.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.tipsyboy.boardApiProject.auth.dto.*;
import study.tipsyboy.boardApiProject.auth.service.AuthService;

@Slf4j
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody SignupRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<LoginResponseDto> reissue(@RequestBody TokenReissueRequestDto requestDto) {
        return ResponseEntity.ok(authService.reissue(requestDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto requestDto) {
        log.info("refresh={}", requestDto.getRefreshToken());
        authService.deleteRefreshToken(requestDto.getRefreshToken());
        return ResponseEntity.ok().build();
    }
}
