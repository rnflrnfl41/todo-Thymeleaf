package me.potato.finaltodo.store.repository;

import me.potato.finaltodo.store.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
