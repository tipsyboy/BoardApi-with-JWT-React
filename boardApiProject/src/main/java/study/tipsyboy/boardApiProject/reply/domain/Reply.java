package study.tipsyboy.boardApiProject.reply.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.posts.domain.Posts;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Reply {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_id")
    private Long id;

    private String content;

    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "posts_id")
    private Posts posts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    // ===== 정적 팩토리 메서드 ===== //
    public static Reply createReply(Member member, Posts posts, String content) {
        Reply reply = new Reply();
        reply.mappingMember(member);
        reply.mappingPosts(posts);
        reply.content = content;
        reply.createDate = LocalDateTime.now();

        return reply;
    }

    private void mappingPosts(Posts posts) {
        this.posts = posts;
        posts.getReplyList().add(this);
    }

    private void mappingMember(Member member) {
        this.member = member;
        member.getReplyList().add(this);
    }

    // ===== 비즈니스 로직 ===== //
    public void update(String content) {
        this.content = content;
    }

}

