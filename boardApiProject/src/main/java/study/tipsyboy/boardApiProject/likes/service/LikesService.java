package study.tipsyboy.boardApiProject.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.auth.exception.AuthException;
import study.tipsyboy.boardApiProject.auth.exception.AuthExceptionType;
import study.tipsyboy.boardApiProject.likes.domain.PostsLikes;
import study.tipsyboy.boardApiProject.likes.domain.PostsLikesRepository;
import study.tipsyboy.boardApiProject.likes.domain.ReplyLikes;
import study.tipsyboy.boardApiProject.likes.domain.ReplyLikesRepository;
import study.tipsyboy.boardApiProject.likes.dto.PostsLikesRequestDto;
import study.tipsyboy.boardApiProject.likes.dto.ReplyLikesRequestDto;
import study.tipsyboy.boardApiProject.likes.exception.LikeException;
import study.tipsyboy.boardApiProject.likes.exception.LikeExceptionType;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.member.domain.MemberRepository;
import study.tipsyboy.boardApiProject.posts.domain.Posts;
import study.tipsyboy.boardApiProject.posts.domain.PostsRepository;
import study.tipsyboy.boardApiProject.posts.exception.PostsException;
import study.tipsyboy.boardApiProject.posts.exception.PostsExceptionType;
import study.tipsyboy.boardApiProject.reply.domain.Reply;
import study.tipsyboy.boardApiProject.reply.domain.ReplyRepository;
import study.tipsyboy.boardApiProject.reply.exception.ReplyException;
import study.tipsyboy.boardApiProject.reply.exception.ReplyExceptionType;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LikesService {

    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;
    private final ReplyRepository replyRepository;
    private final PostsLikesRepository postsLikesRepository;
    private final ReplyLikesRepository replyLikesRepository;

    @Transactional
    public void postsLikes(String email, PostsLikesRequestDto requestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthExceptionType.NOT_FOUND_EMAIL));
        Posts posts = postsRepository.findById(requestDto.getPostsId())
                .orElseThrow(() -> new PostsException(PostsExceptionType.NOT_FOUND_POSTS));

        if (postsLikesRepository.existsByMemberAndPosts(member, posts)) {
            throw new LikeException(LikeExceptionType.DUPLICATE_LIKES);
        }

        posts.addPostsLikes();

        PostsLikes likes = PostsLikes.builder()
                .member(member)
                .posts(posts)
                .build();
        postsLikesRepository.save(likes);
    }

    @Transactional
    public void replyLikes(String email, ReplyLikesRequestDto requestDto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthExceptionType.NOT_FOUND_EMAIL));
        Reply reply = replyRepository.findById(requestDto.getReplyId())
                .orElseThrow(() -> new ReplyException(ReplyExceptionType.REPLY_NOT_FOUND));

        if (replyLikesRepository.existsByMemberAndReply(member, reply)) {
            throw new LikeException(LikeExceptionType.DUPLICATE_LIKES);
        }

        reply.addReplyLikes();

        ReplyLikes replyLikes = ReplyLikes.builder()
                .member(member)
                .reply(reply)
                .build();
        replyLikesRepository.save(replyLikes);
    }
}
