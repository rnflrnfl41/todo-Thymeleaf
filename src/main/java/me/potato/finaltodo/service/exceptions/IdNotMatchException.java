package me.potato.finaltodo.service.exceptions;

public class IdNotMatchException extends CommonException{
    public IdNotMatchException(String code, String message) {
        super(code, message);
    }
}
