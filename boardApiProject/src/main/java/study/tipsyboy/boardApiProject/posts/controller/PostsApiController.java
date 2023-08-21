package study.tipsyboy.boardApiProject.posts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.tipsyboy.boardApiProject.posts.dto.PostsCreateRequestDto;
import study.tipsyboy.boardApiProject.posts.dto.PostsReadResponseDto;
import study.tipsyboy.boardApiProject.posts.service.PostsService;

import java.security.Principal;
import java.util.List;

@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/post")
    public ResponseEntity<Long> createPosts(@RequestBody PostsCreateRequestDto requestDto,
                                            Principal principal) {
        return ResponseEntity.ok(postsService.createPosts(principal.getName(), requestDto));
    }

    @GetMapping("/post/{postsId}")
    public ResponseEntity<PostsReadResponseDto> readById(@PathVariable Long postsId) {
        return ResponseEntity.ok(postsService.findById(postsId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<PostsReadResponseDto>> readByCategory(@PathVariable String category) {
        return ResponseEntity.ok(postsService.findPostsByCategory(category));
    }
}
