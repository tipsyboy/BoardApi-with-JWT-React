package study.tipsyboy.boardApiProject.posts.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.tipsyboy.boardApiProject.posts.domain.Posts;
import study.tipsyboy.boardApiProject.reply.dto.ReplyReadResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostsReadResponseDto {

    private Long id;
    private String nickname;
    private String title;
    private String content;
    private String category;
    private Integer likes;
    private List<ReplyReadResponseDto> replyList = new ArrayList<>();
    private LocalDateTime createDate;

    public static PostsReadResponseDto from(Posts entity) {
        List<ReplyReadResponseDto> replyList = entity.getReplyList().stream()
                .map(Reply -> ReplyReadResponseDto.from(Reply))
                .collect(Collectors.toList());

        return new PostsReadResponseDto(
                entity.getId(),
                entity.getMember().getNickname(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCategory().getKey(),
                entity.getLikes(),
                replyList,
                entity.getCreateDate());
    }
}
