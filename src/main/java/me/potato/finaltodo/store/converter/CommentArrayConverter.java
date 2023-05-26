package me.potato.finaltodo.store.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import me.potato.finaltodo.store.entity.Comment;

import java.util.List;

public class CommentArrayConverter extends JsonArrayConverter<Comment> {
    public CommentArrayConverter() {
        super(new TypeReference<>() {
        });
    }
}
