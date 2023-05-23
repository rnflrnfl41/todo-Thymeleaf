package me.potato.finaltodo.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.controller.dtos.CreateUserRequest;
import me.potato.finaltodo.controller.dtos.LoginRequest;
import me.potato.finaltodo.service.exceptions.IdNotMatchException;
import me.potato.finaltodo.service.exceptions.PasswordNotMatchException;
import me.potato.finaltodo.service.exceptions.PrivateKeyNotExistException;
import me.potato.finaltodo.store.entity.User;
import me.potato.finaltodo.store.repository.UserRepository;
import me.potato.finaltodo.utils.EntityDtoUtil;
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

    @Tracking
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

    @Tracking
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
                    throw new PasswordNotMatchException("10005","Password Not Match..");
                }
                return u;
            }).orElseThrow(() -> new IdNotMatchException("10006","ID not Match.."));
        }else {
            throw new PrivateKeyNotExistException("10007","privateKey is not Exist..");
        }

    }

}
