package me.potato.finaltodo.controller.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private Long postId;
    private String comment;
}
