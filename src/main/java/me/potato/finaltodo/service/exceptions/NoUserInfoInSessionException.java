package me.potato.finaltodo.service.exceptions;

public class NoUserInfoInSessionException extends CommonException{
    public NoUserInfoInSessionException(String code, String message) {
        super(code, message);
    }
}
