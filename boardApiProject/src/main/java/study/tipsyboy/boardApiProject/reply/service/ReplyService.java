package study.tipsyboy.boardApiProject.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.auth.exception.AuthException;
import study.tipsyboy.boardApiProject.auth.exception.AuthExceptionType;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.member.domain.MemberRepository;
import study.tipsyboy.boardApiProject.posts.domain.Posts;
import study.tipsyboy.boardApiProject.posts.domain.PostsRepository;
import study.tipsyboy.boardApiProject.posts.exception.PostsException;
import study.tipsyboy.boardApiProject.posts.exception.PostsExceptionType;
import study.tipsyboy.boardApiProject.reply.domain.Reply;
import study.tipsyboy.boardApiProject.reply.domain.ReplyRepository;
import study.tipsyboy.boardApiProject.reply.dto.ReplyCreateRequestDto;
import study.tipsyboy.boardApiProject.reply.dto.ReplyReadResponseDto;
import study.tipsyboy.boardApiProject.reply.dto.ReplyUpdateRequestDto;
import study.tipsyboy.boardApiProject.reply.exception.ReplyException;
import study.tipsyboy.boardApiProject.reply.exception.ReplyExceptionType;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReplyService {

    private final MemberRepository memberRepository;
    private final PostsRepository postsRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public ReplyReadResponseDto createReply(String email, ReplyCreateRequestDto requestDto) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new AuthException(AuthExceptionType.NOT_FOUND_EMAIL));

        Posts posts = postsRepository.findById(requestDto.getPostsId())
                .orElseThrow(() -> new PostsException(PostsExceptionType.NOT_FOUND_POSTS));

        Reply reply = Reply.createReply(member, posts, requestDto.getContent());
        replyRepository.save(reply);

        return ReplyReadResponseDto.from(reply);
    }

    @Transactional
    public void updateReply(String email, ReplyUpdateRequestDto requestDto) {
        Reply reply = replyRepository.findById(requestDto.getReplyId())
                .orElseThrow(() -> new ReplyException(ReplyExceptionType.REPLY_NOT_FOUND));

        if (!reply.getMember().getEmail().equals(email)) {
            throw new ReplyException(ReplyExceptionType.BAD_REQUEST_AUTHORIZED);
        }

        reply.update(requestDto.getContent());
    }

    @Transactional
    public void delete(String email, Long replyId) {
        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyException(ReplyExceptionType.REPLY_NOT_FOUND));

        if (!reply.getMember().getEmail().equals(email)) {
            throw new ReplyException(ReplyExceptionType.BAD_REQUEST_AUTHORIZED);
        }

        replyRepository.delete(reply);
    }
}
