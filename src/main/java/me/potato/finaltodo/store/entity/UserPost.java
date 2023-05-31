package me.potato.finaltodo.store.entity;

import jakarta.persistence.*;
import lombok.Data;
import me.potato.finaltodo.store.converter.CommentArrayConverter;

import java.util.List;
import java.util.Map;

@Entity
@Data
public class UserPost {

    @Id
    @GeneratedValue
    private Long id;

    private Long userNum;

    private String postContent;

    @Column(columnDefinition = "json")
    @Convert(converter = CommentArrayConverter.class)
    private List<Comment> commentList;

    private Long likeCount;

    private String postingDate;

    private String originalFileName;

    private String storedFileName;

    @PrePersist
    public void prePersist(){
        this.likeCount = this.likeCount == null ? 0 : this.likeCount;
    }





}
