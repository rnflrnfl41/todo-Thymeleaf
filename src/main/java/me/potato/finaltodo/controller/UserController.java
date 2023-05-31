package me.potato.finaltodo.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.controller.dtos.CreateUserRequest;
import me.potato.finaltodo.controller.dtos.LoginRequest;
import me.potato.finaltodo.service.UserService;
import me.potato.finaltodo.store.entity.User;
import me.potato.finaltodo.utils.ZRsaSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/login")
    public String login(Model model) {
        return "login/login";
    }

    @GetMapping("/error")
    public String needLogin(Model model) {
        return "login/noSessionError";
    }

    @GetMapping("/join")
    public String join(Model model) {
        return "login/join";
    }

    @GetMapping("/duplicate")
    @ResponseBody
    public Map<String, Object> idDuplicateCheck(@RequestParam("userId") String userId) {
        Map<String, Object> result = new HashMap<>();
        if(service.duplicateId(userId) == 1) {
            result.put("status","ok");
        }else if(service.duplicateId(userId) == 0) {
            result.put("status","no");
        }
            return result;
    }

    @RequestMapping(value ="/insert", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> insertUser(@RequestBody CreateUserRequest request) {
        Map <String, Object> result =  new HashMap<>();
        User user = service.insertUser(request);
        if(user==null) {
            result.put("result",500);
        }else {
            result.put("result",200);
        }
        return result;
    }

    @GetMapping("/rsa-key")
    @ResponseBody
    //세션에 RSAPrivateKey를 저장 publicKeyModule,publicKeyExponent를 map에 담아 view에 전송
    public Map<String, Object> getRSAKey(HttpServletRequest request){
        Map<String, Object> resultMap = new HashMap<>();
        try {
            ZRsaSecurity security = new ZRsaSecurity();
            PrivateKey privateKey = security.getPrivateKey();

            HttpSession session = request.getSession();
            //만약 개인키가 session에 등록되있다면 제거 후 재 등록
            if(session.getAttribute("_rsaPrivateKey_")!=null) {
                session.removeAttribute("_rsaPrivateKey_");
            }
            session.setAttribute("_rsaPrivateKey_", privateKey);

            String publicKeyModule = security.getRsaPublicKeyModulus();
            String publicKeyExponent = security.getRsaPublicKeyExponent();

            resultMap.put("publicKeyModule", publicKeyModule);
            resultMap.put("publicKeyExponent", publicKeyExponent);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    @RequestMapping(value = "/proc", method = RequestMethod.POST)
    public String loginProc(LoginRequest loginReq, HttpServletRequest httpReq)  {
        try {
           service.loginUser(loginReq,httpReq.getSession());
           return "redirect:/todo/main";
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public void logout(HttpSession session) {
        service.logout(session);
    }


}
