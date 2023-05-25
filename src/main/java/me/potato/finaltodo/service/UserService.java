package me.potato.finaltodo.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.controller.dtos.CreateUserRequest;
import me.potato.finaltodo.controller.dtos.LoginRequest;
import me.potato.finaltodo.service.exceptions.LoginException;
import me.potato.finaltodo.store.entity.User;
import me.potato.finaltodo.store.repository.UserRepository;
import me.potato.finaltodo.utils.EntityDtoUtil;
import me.potato.finaltodo.utils.LoginCheck;
import me.potato.finaltodo.utils.Tracking;
import me.potato.finaltodo.utils.ZRsaSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    @LoginCheck
    public User insertUser(CreateUserRequest request) {
        var user = EntityDtoUtil.userRtoE(request);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
        return user;
    }

    @Tracking
    public int duplicateId(String userId) {
        List<User> userList = repository.findAll();
        int result = 1;
        for(int i=0; i<userList.size(); i++) {
            User user = userList.get(i);
            if(userId.equals(user.getLoginId())) {
                result = 0;
            }
        }
        return result;
    }

    @LoginCheck
    public User loginUser(LoginRequest request,HttpSession session) throws Exception {
        ZRsaSecurity security = new ZRsaSecurity();
        PrivateKey privateKey = (PrivateKey)session.getAttribute("_rsaPrivateKey_");

        if(privateKey!=null) {
            String loginId = security.decryptRSA(privateKey,request.getSecuredUserId());
            String password = security.decryptRSA(privateKey,request.getSecuredUserPassword());
            System.out.println(loginId);
            System.out.println(password);
            Optional<User> user = repository.findUsersByLoginId(loginId);

            return user.map(u -> {
                if(passwordEncoder.matches(password, u.getPassword())){
                    session.setAttribute("user",u);
                    session.setMaxInactiveInterval(30*60);
                }else {
                    throw new LoginException("10005","패스워드가 일치하지 않습니다.");
                }
                return u;
            }).orElseThrow(() -> new LoginException("10006","아이디가 일치하지 않습니다."));
        }else {
            throw new LoginException("10007","privateKey is not Exist..");
        }

    }

    public void logout(HttpSession session) {
        session.removeAttribute("user");
    }

}
