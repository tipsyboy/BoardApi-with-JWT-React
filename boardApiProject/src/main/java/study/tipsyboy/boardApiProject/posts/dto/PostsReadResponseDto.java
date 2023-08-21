package study.tipsyboy.boardApiProject.posts.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.tipsyboy.boardApiProject.posts.domain.Posts;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostsReadResponseDto {

    private Long id;
    private String title;
    private String content;
    private String category;

    public static PostsReadResponseDto from(Posts entity) {
        return new PostsReadResponseDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCategory().getKey());
    }
}
