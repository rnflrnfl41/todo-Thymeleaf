package me.potato.finaltodo.utils;

import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.controller.dtos.CommentRequest;
import me.potato.finaltodo.controller.dtos.CreateTodoRequest;
import me.potato.finaltodo.controller.dtos.CreateUserRequest;
import me.potato.finaltodo.controller.dtos.PostRequest;
import me.potato.finaltodo.service.exceptions.CommonException;
import me.potato.finaltodo.store.entity.Comment;
import me.potato.finaltodo.store.entity.Todo;
import me.potato.finaltodo.store.entity.User;
import me.potato.finaltodo.store.entity.UserPost;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class EntityDtoUtil {



    public static Todo todoRtoE(CreateTodoRequest request) {
        Todo todo = new Todo();
        todo.setUserNo(request.getUserNo());
        todo.setCreatedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        todo.setItem(request.getItem());
        todo.setStatus("registered");
        todo.setCompletedTime("progressing");
        return todo;
    }

    public static User userRtoE(CreateUserRequest request) {
        User user = new User();
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setLoginId(request.getLoginId());
        return user;
    }


    public static Comment postCommentRtoE(CommentRequest request) {
        Comment comment = new Comment();
        comment.setComment(request.getComment());
        comment.setPostId(request.getPostId());
        return comment;
    }

}
