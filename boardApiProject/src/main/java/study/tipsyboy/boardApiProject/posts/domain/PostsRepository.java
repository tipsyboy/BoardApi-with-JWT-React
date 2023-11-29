package study.tipsyboy.boardApiProject.posts.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface PostsRepository extends JpaRepository<Posts, Long> {
    Page<Posts> findByCategory(Category category, Pageable pageable);

//    @Query("select p from Posts p" +
//            " join fetch p.member m" +
//            " left join fetch p.replyList r")
//    List<Posts> findByCategory(Category category);
}
