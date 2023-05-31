package me.potato.finaltodo.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.controller.dtos.CommentRequest;
import me.potato.finaltodo.controller.dtos.PostRequest;
import me.potato.finaltodo.service.PostService;
import me.potato.finaltodo.store.entity.UserPost;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService service;


    @PostMapping("/comment")
    @ResponseBody
    public UserPost insertCommentToPost(@RequestBody CommentRequest commentRequest, HttpSession session){
        return service.insertComment(commentRequest,session);
    }

    @ResponseBody
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Map<String, Object> insertPost(@ModelAttribute PostRequest request, HttpSession session) {
        Map<String, Object> resultMap = new HashMap<>();
        if(service.insertPost(request,session) != null) {
            resultMap.put("resultCode", 200);
        }else {
            resultMap.put("resultCode",500);
        }


        return resultMap;
    }

    @GetMapping("/likeYes")
    @ResponseBody
    public void likeYes(@RequestParam("postId") Long postId) {
        service.likeYes(postId);
    }

    @GetMapping("/likeNo")
    @ResponseBody
    public void likeNo(@RequestParam("postId") Long postId) {
        service.likeNo(postId);
    }

}
