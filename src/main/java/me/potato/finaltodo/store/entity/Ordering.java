package me.potato.finaltodo.store.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.potato.finaltodo.store.converter.TodoArrayConverter;

import java.util.List;

@Entity
@NoArgsConstructor
@Data
public class Ordering {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Long userNo;

    @Column(columnDefinition = "json")
    @Convert(converter = TodoArrayConverter.class)
    private List<Todo> todoList;

}
