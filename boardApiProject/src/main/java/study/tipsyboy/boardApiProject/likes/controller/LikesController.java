package study.tipsyboy.boardApiProject.likes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.tipsyboy.boardApiProject.likes.dto.PostsLikesRequestDto;
import study.tipsyboy.boardApiProject.likes.dto.ReplyLikesRequestDto;
import study.tipsyboy.boardApiProject.likes.service.LikesService;

import java.security.Principal;

@RequestMapping("/api/likes")
@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/post")
    public ResponseEntity<Void> likesPosts(@RequestBody PostsLikesRequestDto requestDto,
                                           Principal principal) {
        likesService.postsLikes(principal.getName(), requestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reply")
    public ResponseEntity<Void> likesReply(@RequestBody ReplyLikesRequestDto requestDto,
                                           Principal principal) {
        likesService.replyLikes(principal.getName(), requestDto);
        return ResponseEntity.ok().build();
    }
}
