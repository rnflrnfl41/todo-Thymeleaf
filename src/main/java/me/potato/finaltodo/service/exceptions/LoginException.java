package me.potato.finaltodo.service.exceptions;

public class LoginException extends CommonException{
    public LoginException(String code, String message) {
        super(code, message);
    }
}
