package me.potato.finaltodo.store.repository;

import me.potato.finaltodo.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUsersByLoginId(String loginId);
}
