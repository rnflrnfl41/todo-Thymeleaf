package me.potato.finaltodo.service.exceptions;

public class PasswordNotMatchException extends  CommonException{
    public PasswordNotMatchException(String code, String message) {
        super(code, message);
    }
}
