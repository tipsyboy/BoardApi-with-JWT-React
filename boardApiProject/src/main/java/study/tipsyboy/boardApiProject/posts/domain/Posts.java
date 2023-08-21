package study.tipsyboy.boardApiProject.posts.domain;

import lombok.*;
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

    // ===== 정적 팩토리 메서드 ===== //
    public static Posts createPosts(String title, String content, Category category) {
        Posts posts = new Posts();
        posts.title = title;
        posts.content = content;
        posts.category = category;
        posts.createDate = LocalDateTime.now();

        return posts;
    }
}
