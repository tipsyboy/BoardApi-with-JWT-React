package study.tipsyboy.boardApiProject.posts.domain;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.reply.domain.Reply;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Posts {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "posts_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "posts")
    private List<Reply> replyList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private int likes;

    // ===== 정적 팩토리 메서드 ===== //
    public static Posts createPosts(Member member, String title, String content, Category category) {
        Posts posts = new Posts();
        posts.mappingMember(member);
        posts.title = title;
        posts.content = content;
        posts.category = category;
        posts.createDate = LocalDateTime.now();
        posts.likes = 0;

        return posts;
    }

    private void mappingMember(Member member) {
        this.member = member;
        member.getPostsList().add(this);
    }

    // ===== 비즈니스 로직 ===== //
    public void updatePosts(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void addPostsLikes() {
        this.likes += 1;
    }
}
