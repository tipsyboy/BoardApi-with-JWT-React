package study.tipsyboy.boardApiProject.posts.exception;


import study.tipsyboy.boardApiProject.exception.ExceptionType;
import study.tipsyboy.boardApiProject.exception.GlobalBaseException;

public class PostsException extends GlobalBaseException {

    public PostsException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
