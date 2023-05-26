package me.potato.finaltodo.store.repository;

import me.potato.finaltodo.store.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findByUserNum(Long id);

}
