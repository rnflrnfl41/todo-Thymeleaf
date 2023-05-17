package me.potato.finaltodo.controller.dtos;

import lombok.Data;

@Data
public class CreateTodoRequest {
    String item;
    Long userNo;
}
