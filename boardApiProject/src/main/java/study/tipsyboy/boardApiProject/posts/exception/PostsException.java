package study.tipsyboy.boardApiProject.posts.exception;


public class PostsException extends RuntimeException {
    public PostsException(PostsExceptionType postsExceptionType) {
        super(postsExceptionType.getMessage());
    }
}
