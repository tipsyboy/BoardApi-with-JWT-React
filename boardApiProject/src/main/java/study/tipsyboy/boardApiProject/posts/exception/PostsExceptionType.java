package study.tipsyboy.boardApiProject.posts.exception;

import lombok.Getter;

@Getter
public enum PostsExceptionType {
    NOT_FOUND_POSTS("게시글이 존재하지 않습니다.");

    private final String message;

    PostsExceptionType(String message) {
        this.message = message;
    }
}
