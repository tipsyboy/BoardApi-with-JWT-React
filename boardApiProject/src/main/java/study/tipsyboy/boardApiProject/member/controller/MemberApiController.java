package study.tipsyboy.boardApiProject.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.tipsyboy.boardApiProject.member.dto.MemberProfileUpdateDto;
import study.tipsyboy.boardApiProject.member.service.MemberService;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/api/members")
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PutMapping("/member")
    public ResponseEntity<Void> update(@Valid @RequestBody MemberProfileUpdateDto requestDto,
                                       Principal principal) {

        /*
           THINK:
            하지만 이 경우, 회원정보는 수정되었지만 이전 토큰이 여전히 남아서 사용된다.
            이를 방지하기 위해서 새로운 토큰을 발행하여 [토큰 블랙리스트,  토큰 버전관리, 만료시간을 짧게] 등의 방법을 사용할 수 있으나 JWT 의 장점이 일부분 사라질 수 있다.
            이러한 트레이드 오프를 생각해보고 어떤 방식을 선택할지 고민해보자.
            현재는 AccessToken 의 만료기간을 30분으로 짧게 잡아서 사용한다.
         */
        memberService.updateMember(principal.getName(), requestDto);
        return ResponseEntity.ok().build();
    }

}
