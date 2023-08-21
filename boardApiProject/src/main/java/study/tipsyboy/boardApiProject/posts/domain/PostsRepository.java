package study.tipsyboy.boardApiProject.posts.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts, Long> {
    List<Posts> findByCategory(Category category);
}
