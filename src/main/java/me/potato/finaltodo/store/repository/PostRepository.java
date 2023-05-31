package me.potato.finaltodo.store.repository;

import me.potato.finaltodo.store.entity.UserPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<UserPost, Long> {

    public List<UserPost> findByUserNum(Long userNum);
}
