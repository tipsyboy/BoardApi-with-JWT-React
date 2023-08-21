package study.tipsyboy.boardApiProject.reply.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.tipsyboy.boardApiProject.posts.domain.Posts;
import study.tipsyboy.boardApiProject.posts.domain.PostsRepository;
import study.tipsyboy.boardApiProject.posts.exception.PostsException;
import study.tipsyboy.boardApiProject.posts.exception.PostsExceptionType;
import study.tipsyboy.boardApiProject.reply.domain.Reply;
import study.tipsyboy.boardApiProject.reply.domain.ReplyRepository;
import study.tipsyboy.boardApiProject.reply.dto.ReplyCreateRequestDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReplyService {

    private final PostsRepository postsRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public Long createReply(ReplyCreateRequestDto requestDto) {

        Posts posts = postsRepository.findById(requestDto.getPostsId())
                .orElseThrow(() -> new PostsException(PostsExceptionType.NOT_FOUND_POSTS));

        Reply reply = Reply.createReply(posts, requestDto.getContent());
        replyRepository.save(reply);

        return reply.getId();
    }

}
