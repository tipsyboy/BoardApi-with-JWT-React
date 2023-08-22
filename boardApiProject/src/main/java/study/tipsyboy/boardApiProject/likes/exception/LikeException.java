package study.tipsyboy.boardApiProject.likes.exception;

public class LikeException extends RuntimeException {

    public LikeException(LikeExceptionType likeExceptionType) {
        super(likeExceptionType.getMessage());
    }
}
