package study.tipsyboy.boardApiProject.posts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.posts.domain.Category;
import study.tipsyboy.boardApiProject.posts.domain.Posts;
import study.tipsyboy.boardApiProject.posts.domain.PostsRepository;
import study.tipsyboy.boardApiProject.posts.dto.PostsCreateRequestDto;
import study.tipsyboy.boardApiProject.posts.dto.PostsReadResponseDto;
import study.tipsyboy.boardApiProject.posts.exception.PostsException;
import study.tipsyboy.boardApiProject.posts.exception.PostsExceptionType;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostsRepository postsRepository;

    @Transactional
    public Long createPosts(PostsCreateRequestDto requestDto) {
        Posts posts = Posts.createPosts(
                requestDto.getTitle(),
                requestDto.getContent(),
                Category.getCategoryByKey(requestDto.getCategory()));

        Posts savedPosts = postsRepository.save(posts);
        return savedPosts.getId();
    }

    public PostsReadResponseDto findById(Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new PostsException(PostsExceptionType.NOT_FOUND_POSTS));
        
        return PostsReadResponseDto.from(posts);
    }

    public List<PostsReadResponseDto> findPostsByCategory(String category) {
        return postsRepository.findByCategory(Category.getCategoryByKey(category)).stream()
                .map(Posts -> PostsReadResponseDto.from(Posts))
                .collect(Collectors.toList());
    }
}
