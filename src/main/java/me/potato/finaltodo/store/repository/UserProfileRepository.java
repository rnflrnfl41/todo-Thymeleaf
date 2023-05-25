package me.potato.finaltodo.store.repository;

import me.potato.finaltodo.store.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<Long, UserProfile> {
}
