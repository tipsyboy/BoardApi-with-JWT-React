package study.tipsyboy.boardApiProject.reply.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.tipsyboy.boardApiProject.reply.dto.ReplyCreateRequestDto;
import study.tipsyboy.boardApiProject.reply.service.ReplyService;

@RequestMapping("/api/replies")
@RequiredArgsConstructor
@RestController
public class ReplyApiController {

    private final ReplyService replyService;

    @PostMapping("/reply")
    public ResponseEntity<Long> createReply(@RequestBody ReplyCreateRequestDto requestDto) {
        return ResponseEntity.ok(replyService.createReply(requestDto));
    }
}
