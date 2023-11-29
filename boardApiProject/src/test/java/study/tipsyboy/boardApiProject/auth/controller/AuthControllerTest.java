package study.tipsyboy.boardApiProject.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.auth.dto.LoginRequestDto;
import study.tipsyboy.boardApiProject.auth.dto.LoginResponseDto;
import study.tipsyboy.boardApiProject.auth.dto.SignupRequestDto;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.member.domain.MemberRepository;
import study.tipsyboy.boardApiProject.member.domain.MemberRole;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입을 한다.")
    public void signupMember() throws Exception {
        // given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email("test@test.com")
                .password("password123")
                .nickname("tester")
                .build();
        String json = objectMapper.writeValueAsString(signupRequestDto);

        // expected
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입을 실패 - 이메일 중복")
    public void signupFailureEmailDup() throws Exception {
        // given
        Member member = Member.builder()
                .email("test@test.com")
                .password("password123")
                .nickname("tester1")
                .memberRole(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);

        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email("test@test.com")
                .password("password123")
                .nickname("tester2")
                .build();
        String json = objectMapper.writeValueAsString(signupRequestDto);

        // expected
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    @DisplayName("회원가입을 실패 - 닉네임 중복")
    public void signupFailureNicknameDup() throws Exception {
        // given
        Member member = Member.builder()
                .email("test1@test.com")
                .password("password123")
                .nickname("tester")
                .memberRole(MemberRole.MEMBER)
                .build();
        memberRepository.save(member);

        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email("test2@test.com")
                .password("password123")
                .nickname("tester")
                .build();
        String json = objectMapper.writeValueAsString(signupRequestDto);

        // expected
        mockMvc.perform(post("/api/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isConflict())
                .andDo(print());
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

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .email("test@test.com")
                .password("password123")
                .build();
        String json = objectMapper.writeValueAsString(loginRequestDto);

        // expected
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andDo(print());
    }

}