package study.tipsyboy.boardApiProject.posts.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class PostsCreateRequestDto {

    private String title;
    private String content;
    private String category;
}
