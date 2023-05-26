package me.potato.finaltodo.store.entity;

import jakarta.persistence.*;
import lombok.Data;
import me.potato.finaltodo.store.converter.CommentArrayConverter;
import me.potato.finaltodo.store.converter.TodoArrayConverter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Entity
@Data
public class UserPost {

    @Id
    @GeneratedValue
    private Long id;

    private String postContent;

    @Column(columnDefinition = "json")
    @Convert(converter = CommentArrayConverter.class)
    private List<Comment> comment;

    private Long likeCount;

    private String postingDate;

    private String originalFileName;

    private String storedFileName;



}
