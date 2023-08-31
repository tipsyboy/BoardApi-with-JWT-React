package study.tipsyboy.boardApiProject.posts.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findByCategory(Category category, Pageable pageable);
}
