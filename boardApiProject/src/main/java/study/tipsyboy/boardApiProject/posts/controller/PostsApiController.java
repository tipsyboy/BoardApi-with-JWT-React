package study.tipsyboy.boardApiProject.posts.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.tipsyboy.boardApiProject.posts.dto.PostsCreateRequestDto;
import study.tipsyboy.boardApiProject.posts.dto.PostsReadResponseDto;
import study.tipsyboy.boardApiProject.posts.dto.PostsUpdateRequestDto;
import study.tipsyboy.boardApiProject.posts.service.PostsService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Slf4j
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<Long> createPosts(@RequestBody PostsCreateRequestDto requestDto,
                                            Principal principal) {
        return ResponseEntity.ok(postsService.createPosts(principal.getName(), requestDto));
    }

    @PutMapping
    public ResponseEntity<Void> updatePosts(@Valid @RequestBody PostsUpdateRequestDto requestDto,
                                            Principal principal) {
        postsService.updatePosts(principal.getName(), requestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePosts(@RequestParam("postsId") Long postsId,
                                            Principal principal) {
        postsService.delete(principal.getName(), postsId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postsId}")
    public ResponseEntity<PostsReadResponseDto> readById(@PathVariable Long postsId) {
        return ResponseEntity.ok(postsService.findById(postsId));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<PostsReadResponseDto>> readByCategory(@PathVariable String category) {
        return ResponseEntity.ok(postsService.findPostsByCategory(category));
    }
}
