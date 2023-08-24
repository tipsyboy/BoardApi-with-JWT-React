package study.tipsyboy.boardApiProject.reply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.tipsyboy.boardApiProject.reply.dto.ReplyCreateRequestDto;
import study.tipsyboy.boardApiProject.reply.dto.ReplyReadResponseDto;
import study.tipsyboy.boardApiProject.reply.dto.ReplyUpdateRequestDto;
import study.tipsyboy.boardApiProject.reply.service.ReplyService;

import javax.validation.Valid;
import java.security.Principal;

@RequestMapping("/api/replies")
@RequiredArgsConstructor
@RestController
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping
    public ResponseEntity<ReplyReadResponseDto> createReply(@RequestBody ReplyCreateRequestDto requestDto,
                                                            Principal principal) {
        return ResponseEntity.ok(replyService.createReply(principal.getName(), requestDto));
    }

    @PutMapping
    public ResponseEntity<Void> editReply(@Valid @RequestBody ReplyUpdateRequestDto requestDto,
                                            Principal principal) {
        replyService.updateReply(principal.getName(), requestDto);
        return ResponseEntity.ok().build();
    }
}
