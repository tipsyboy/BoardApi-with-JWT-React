package study.tipsyboy.boardApiProject.likes.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import study.tipsyboy.boardApiProject.member.domain.Member;
import study.tipsyboy.boardApiProject.reply.domain.Reply;

@Repository
public interface ReplyLikesRepository extends JpaRepository<ReplyLikes, Long> {
    boolean existsByMemberAndReply(Member member, Reply reply);
}
