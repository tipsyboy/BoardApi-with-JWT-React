package study.tipsyboy.boardApiProject.likes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.posts.domain.Posts;

@Repository
public interface PostsLikesRepository extends JpaRepository<PostsLikes, Long> {
    boolean existsByMemberAndPosts(Member member, Posts posts);
}
