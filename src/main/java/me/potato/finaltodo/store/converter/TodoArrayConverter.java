package me.potato.finaltodo.store.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import me.potato.finaltodo.store.entity.Todo;

public class TodoArrayConverter extends JsonArrayConverter<Todo>{
    public TodoArrayConverter() {
        super(new TypeReference<>() {
        });
    }
}
