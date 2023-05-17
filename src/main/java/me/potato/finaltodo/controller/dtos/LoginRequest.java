package me.potato.finaltodo.controller.dtos;

import lombok.Data;

@Data
public class LoginRequest {

    private String securedUserId;
    private String securedUserPassword;

}
