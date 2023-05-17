package me.potato.finaltodo.service.exceptions;

public class PrivateKeyNotExistException extends CommonException{
    public PrivateKeyNotExistException(String code, String message) {
        super(code, message);
    }
}
