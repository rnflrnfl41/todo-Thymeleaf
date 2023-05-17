package me.potato.finaltodo.controller.dtos;

import lombok.Data;

@Data
public class CreateUserRequest {

    private String loginId;
    private String password;
    private String name;

}
