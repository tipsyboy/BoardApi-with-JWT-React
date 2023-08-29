package study.tipsyboy.boardApiProject.auth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.tipsyboy.boardApiProject.auth.dto.LoginRequestDto;
import study.tipsyboy.boardApiProject.auth.dto.LoginResponseDto;
import study.tipsyboy.boardApiProject.auth.dto.SignupRequestDto;
import study.tipsyboy.boardApiProject.auth.dto.TokenReissueRequestDto;
import study.tipsyboy.boardApiProject.auth.service.AuthService;

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
}
