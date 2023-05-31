package me.potato.finaltodo.service.exceptions;

public class PostNotExistException extends CommonException{
    public PostNotExistException(String code, String message) {
        super(code, message);
    }
}
