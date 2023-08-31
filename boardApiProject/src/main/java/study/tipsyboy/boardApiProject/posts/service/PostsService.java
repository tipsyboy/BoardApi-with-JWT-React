package study.tipsyboy.boardApiProject.posts.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.auth.exception.AuthException;
import study.tipsyboy.boardApiProject.auth.exception.AuthExceptionType;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.member.domain.MemberRepository;
import study.tipsyboy.boardApiProject.posts.domain.Category;
import study.tipsyboy.boardApiProject.posts.domain.Posts;
import study.tipsyboy.boardApiProject.posts.domain.PostsRepository;
import study.tipsyboy.boardApiProject.posts.dto.PostsCreateRequestDto;
import study.tipsyboy.boardApiProject.posts.dto.PostsReadResponseDto;
import study.tipsyboy.boardApiProject.posts.dto.PostsUpdateRequestDto;
import study.tipsyboy.boardApiProject.posts.exception.PostsException;
import study.tipsyboy.boardApiProject.posts.exception.PostsExceptionType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostsService {

    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;
    private static final Integer POSTS_PER_PAGE = 15;

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

    public Page<PostsReadResponseDto> findPostsByCategory(String category, int page) {

        int convertPage = convertToStartingIndexOne(page);

        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        PageRequest pageable = PageRequest.of(convertPage, POSTS_PER_PAGE, Sort.by(sorts));

        return postsRepository.findByCategory(Category.getCategoryByKey(category), pageable)
                .map(Posts -> PostsReadResponseDto.from(Posts));
    }

    private int convertToStartingIndexOne(int page) {
        if (page <= 0) {
            return 0;
        }
        return page - 1;
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

    @Transactional
    public void delete(String email, Long postsId) {
        Posts posts = postsRepository.findById(postsId)
                .orElseThrow(() -> new PostsException(PostsExceptionType.NOT_FOUND_POSTS));

        if (!posts.getMember().getEmail().equals(email)) {
            throw new PostsException(PostsExceptionType.BAD_REQUEST_AUTHORIZED);
        }

        postsRepository.delete(posts);
    }
}
