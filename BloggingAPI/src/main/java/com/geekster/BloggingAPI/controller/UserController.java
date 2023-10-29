package com.geekster.BloggingAPI.controller;

import com.geekster.BloggingAPI.model.Post;
import com.geekster.BloggingAPI.model.User;
import com.geekster.BloggingAPI.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("signUp")
    public String userSignUp(@Valid @RequestBody User user){
        return userService.userSignUp(user);
    }

    //sign in
    @PostMapping("user/signIn/{email}/{password}")
    public String userSignIn(@PathVariable String email, @PathVariable String password)
    {
        return userService.userSignIn(email,password);
    }

    //sign out
    @DeleteMapping("user/signOut")
    public String userSignOut(@RequestParam String email, @RequestParam String token)
    {
        return userService.userSignOut(email,token);
    }

    // create a post
    @PostMapping("BlogPost")
    public String createPost(@RequestParam String email,@RequestParam String tokenValue, @RequestBody Post instaPost)
    {
        return userService.createPost(email,tokenValue,instaPost);
    }

    // get a post
    @GetMapping("BlogPost/{postId}")
    public String getPostContentByPostId(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long postId)
    {
        return userService.getPostContentByPostId(email,tokenValue,postId);
    }

    @PutMapping("BlogPost/Location/{location}/{postId}")
    public String updatePostLocationByPostId(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long postId,@PathVariable String location){
        return userService.updatePostLocationByPostId(email,tokenValue,postId,location);
    }


    // delete a post
    @DeleteMapping("Post/{postId}")
    public String deletePost(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long postId)
    {
        return userService.deletePost(email,tokenValue,postId);
    }


    // comment api
    // add comment
    @PostMapping("comment/post/{postId}")
    public String addComment(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long postId,@RequestBody String commentBody )
    {
        return userService.addComment(email,tokenValue,commentBody,postId);
    }

    @GetMapping("comment/{postId}")
    public List<String> getCommentByPostId(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long postId)
    {
        return userService.getCommentByPostId(email,tokenValue,postId);
    }

    @PutMapping("Edit/Comment/{commentId}/{newComment}")
    public String editCommentByCommentId(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long commentId,@PathVariable String newComment){
        return userService.editCommentByCommentId(email,tokenValue,commentId,newComment);
    }

    @DeleteMapping("comment/{postId}")
    public String deleteComment(@RequestParam String email, @RequestParam String tokenValue, @PathVariable Long commentId)
    {
        return userService.deleteComment(email,tokenValue,commentId);
    }




}
