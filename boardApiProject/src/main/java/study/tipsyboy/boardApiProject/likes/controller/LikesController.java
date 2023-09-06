package study.tipsyboy.boardApiProject.likes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.tipsyboy.boardApiProject.likes.dto.LikesExceptionResponseDto;
import study.tipsyboy.boardApiProject.likes.dto.PostsLikesRequestDto;
import study.tipsyboy.boardApiProject.likes.dto.ReplyLikesRequestDto;
import study.tipsyboy.boardApiProject.likes.exception.LikeException;
import study.tipsyboy.boardApiProject.likes.service.LikesService;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RequestMapping("/api/likes")
@RequiredArgsConstructor
@RestController
public class LikesController {

    private final LikesService likesService;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LikeException.class)
    public LikesExceptionResponseDto handleLikeException(LikeException e) {
        return new LikesExceptionResponseDto(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

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
