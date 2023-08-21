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
        memberService.updateMember(principal.getName(), requestDto);
        return ResponseEntity.ok().build();
    }

}
