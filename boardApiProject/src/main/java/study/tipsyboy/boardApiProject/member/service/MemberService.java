package study.tipsyboy.boardApiProject.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.auth.exception.AuthException;
import study.tipsyboy.boardApiProject.auth.exception.AuthExceptionType;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.member.domain.MemberRepository;
import study.tipsyboy.boardApiProject.member.dto.MemberProfileUpdateDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updateMember(String email, MemberProfileUpdateDto requestDto) {
        if (memberRepository.existsByNickname(requestDto.getNickname())) {
            throw new AuthException(AuthExceptionType.DUPLICATE_NICKNAME);
        }

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthExceptionType.NOT_EXISTS_EMAIL));

        member.update(requestDto.getNickname(), requestDto.getPassword(), passwordEncoder);
    }
}
