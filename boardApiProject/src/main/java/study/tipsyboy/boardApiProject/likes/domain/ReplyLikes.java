package study.tipsyboy.boardApiProject.likes.domain;

import lombok.*;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.posts.domain.Posts;
import study.tipsyboy.boardApiProject.reply.domain.Reply;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ReplyLikes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_likes_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id")
    private Reply reply;

}
