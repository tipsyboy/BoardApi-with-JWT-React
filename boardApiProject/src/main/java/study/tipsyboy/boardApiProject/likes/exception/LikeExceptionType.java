package study.tipsyboy.boardApiProject.likes.exception;

import lombok.Getter;

@Getter
public enum LikeExceptionType {
    DUPLICATE_LIKES("이미 추천하였습니다.");

    private final String message;

    LikeExceptionType(String message) {
        this.message = message;
    }
}
