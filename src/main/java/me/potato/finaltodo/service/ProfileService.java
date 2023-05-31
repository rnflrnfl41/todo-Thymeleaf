package me.potato.finaltodo.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.store.entity.User;
import me.potato.finaltodo.store.entity.UserProfile;
import me.potato.finaltodo.store.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserProfileRepository profileRepository;

    public UserProfile getProfile(HttpSession session) {
        User user = (User)session.getAttribute("user");
        Optional<UserProfile> profileVO = profileRepository.findByUserNum(user.getId());
        return profileVO.orElseGet(() -> {
            UserProfile newProfile = new UserProfile();
            newProfile.setUserNum(user.getId());
            newProfile.setUserLoginId(user.getLoginId());
            newProfile.setUserName(user.getName());
            return profileRepository.save(newProfile);
        });
    }
}
