package me.potato.finaltodo.store.repository;

import me.potato.finaltodo.store.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
