package me.potato.finaltodo.service.exceptions;

public class TodoListNotExistException extends CommonException {

    public TodoListNotExistException(String code, String message) {
        super(code, message);
    }
}
