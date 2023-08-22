package study.tipsyboy.boardApiProject.posts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.auth.exception.AuthException;
import study.tipsyboy.boardApiProject.auth.exception.AuthExceptionType;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.member.domain.MemberRepository;
import study.tipsyboy.boardApiProject.member.dto.MemberProfileUpdateDto;
import study.tipsyboy.boardApiProject.posts.domain.Category;
import study.tipsyboy.boardApiProject.posts.domain.Posts;
import study.tipsyboy.boardApiProject.posts.domain.PostsRepository;
import study.tipsyboy.boardApiProject.posts.dto.PostsCreateRequestDto;
import study.tipsyboy.boardApiProject.posts.dto.PostsReadResponseDto;
import study.tipsyboy.boardApiProject.posts.dto.PostsUpdateRequestDto;
import study.tipsyboy.boardApiProject.posts.exception.PostsException;
import study.tipsyboy.boardApiProject.posts.exception.PostsExceptionType;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostsService {

    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;

    @Transactional
    public Long createPosts(String email, PostsCreateRequestDto requestDto) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthExceptionType.NOT_FOUND_EMAIL));

        Posts posts = Posts.createPosts(
                member,
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

    @Transactional
    public void updatePosts(String email, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(requestDto.getPostsId())
                .orElseThrow(() -> new PostsException(PostsExceptionType.NOT_FOUND_POSTS));

        if (!posts.getMember().getEmail().equals(email)) {
            throw new PostsException(PostsExceptionType.BAD_REQUEST_AUTHORIZED);
        }

        posts.updatePosts(
                requestDto.getTitle(),
                requestDto.getContent(),
                Category.getCategoryByKey(requestDto.getCategory()));
    }
}
