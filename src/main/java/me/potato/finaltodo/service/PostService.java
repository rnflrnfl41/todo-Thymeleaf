package me.potato.finaltodo.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import me.potato.finaltodo.controller.dtos.CommentRequest;
import me.potato.finaltodo.controller.dtos.PostRequest;
import me.potato.finaltodo.service.exceptions.PostNotExistException;
import me.potato.finaltodo.store.entity.Comment;
import me.potato.finaltodo.store.entity.User;
import me.potato.finaltodo.store.entity.UserPost;
import me.potato.finaltodo.store.repository.CommentRepository;
import me.potato.finaltodo.store.repository.PostRepository;
import me.potato.finaltodo.utils.CommonUtils;
import me.potato.finaltodo.utils.EntityDtoUtil;
import me.potato.finaltodo.utils.aspect.Tracking;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Tracking
    public List<UserPost> getPost(HttpSession session) {
        User user = (User)session.getAttribute("user");
        return postRepository.findByUserNum(user.getId());
    }

    @Tracking
    public UserPost insertPost(@RequestBody PostRequest request, HttpSession session){
        UserPost post = new UserPost();
        Map<String,String> fileParam;
        User user = (User)session.getAttribute("user");
        post.setPostContent(request.getContent());
        post.setUserNum(user.getId());
        post.setPostingDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        try {
            fileParam = CommonUtils.uploadFile(request.getMultipartFile());
            post.setOriginalFileName(fileParam.get("originalFileName"));
            post.setStoredFileName(fileParam.get("storedFileName"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return postRepository.save(post);
    }

    @Tracking
    public UserPost insertComment(@RequestBody CommentRequest commentRequest, HttpSession session) {
        Optional<UserPost> post = postRepository.findById(commentRequest.getPostId());
        List<Comment> newCommentList = new ArrayList<>();
        User user = (User)session.getAttribute("user");
        return post.map(p -> {
            var comment = EntityDtoUtil.postCommentRtoE(commentRequest);
            comment.setUserId(user.getId());
            comment.setUserLoginId(user.getLoginId());
            List<Comment> commentList = p.getCommentList();
            if(p.getCommentList() != null) {
                commentList.add(comment);
                p.setCommentList(commentList);
            }else {
                newCommentList.add(comment);
                p.setCommentList(newCommentList);
            }
            commentRepository.save(comment);
            return postRepository.save(p);
        }).orElseThrow(() -> new PostNotExistException("10001","Post Not Exist..."));

    }

    public void likeYes(Long postId) {
        Optional<UserPost> post = postRepository.findById(postId);
        post.map(p -> {
            Long like = p.getLikeCount() + 1;
            p.setLikeCount(like);
            return postRepository.save(p);
        }).orElseThrow(() -> new PostNotExistException("10001","Post Not Exist..."));
    }

    public void likeNo(Long postId) {
        Optional<UserPost> post = postRepository.findById(postId);
        post.map(p -> {
            Long like = p.getLikeCount() -1;
            p.setLikeCount(like);
            return postRepository.save(p);
        }).orElseThrow(() -> new PostNotExistException("10001","Post Not Exist..."));
    }
}
