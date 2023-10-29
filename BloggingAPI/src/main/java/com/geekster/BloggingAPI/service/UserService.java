package com.geekster.BloggingAPI.service;

import com.geekster.BloggingAPI.model.AuthenticationToken;
import com.geekster.BloggingAPI.model.Comment;
import com.geekster.BloggingAPI.model.Post;
import com.geekster.BloggingAPI.model.User;
import com.geekster.BloggingAPI.repo.IUserRepo;
import com.geekster.BloggingAPI.service.emailUtility.EmailHandler;
import com.geekster.BloggingAPI.service.hashingUtility.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    IUserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    public String userSignUp(User newUser) {
        //check if already exist -> Not allowed : try logging in

        String newEmail = newUser.getUserEmail();

        User existingUser = userRepo.findFirstByUserEmail(newEmail);

        if(existingUser != null)
        {
            return "email already in use";
        }

        // passwords are encrypted before we store it in the table

        String signUpPassword = newUser.getUserPassword();

        try {
            String encryptedPassword = PasswordEncryptor.encrypt(signUpPassword);

            newUser.setUserPassword(encryptedPassword);


            // patient table - save patient
            userRepo.save(newUser);
            return "Blogging user registered";

        } catch (NoSuchAlgorithmException e) {

            return "Internal Server issues while saving password, try again later!!!";
        }
    }

    public String userSignIn(String email, String password) {
        //check if the email is there in your tables
        //sign in only possible if this person ever signed up


        User existingUser = userRepo.findFirstByUserEmail(email);

        if(existingUser == null)
        {
            return "Not a valid email, Please sign up first !!!";
        }

        //password should be matched

        try {
            String encryptedPassword = PasswordEncryptor.encrypt(password);

            if(existingUser.getUserPassword().equals(encryptedPassword))
            {
                // return a token for this sign in
                AuthenticationToken token  = new AuthenticationToken(existingUser);

                if(EmailHandler.sendEmail(email,"otp after login", token.getTokenValue())) {
                    authenticationService.createToken(token);
                    return "check email for otp/token!!!";
                }
                else {
                    return "error while generating token!!!";
                }

            }
            else {
                //password was wrong!!!
                return "Invalid Credentials!!!";
            }

        } catch (NoSuchAlgorithmException e) {

            return "Internal Server issues while saving password, try again later!!!";
        }
    }

    public String userSignOut(String email, String tokenValue) {

        if(authenticationService.authenticate(email,tokenValue)) {
            authenticationService.deleteToken(tokenValue);
            return "Sign Out successful!!";
        }
        else {
            return "Un Authenticated access!!!";
        }
    }

    public String createPost(String email, String tokenValue, Post blogPost) {
        if(authenticationService.authenticate(email,tokenValue)) {

            User existingUser = userRepo.findFirstByUserEmail(email);
            blogPost.setPostOwner(existingUser);




            postService.createPost(blogPost);
            return blogPost.getPostType() + " posted!!";

        }
        else {
            return "Un Authenticated access!!!";
        }

    }

    public String getPostContentByPostId(String email, String tokenValue, Long postId) {
        if(authenticationService.authenticate(email,tokenValue)) {

            Post post = postService.getPostById(postId);

            return post.getPostContent();

        }
        else {
            return "Un Authenticated access!!!";
        }
    }

    public String deletePost(String email, String tokenValue, Long postId) {
        if(authenticationService.authenticate(email,tokenValue)) {

            Post instaPost =  postService.getPostById(postId);
            String  postOwnerEmail =  instaPost.getPostOwner().getUserEmail();

            if(email.equals(postOwnerEmail))
            {


                //finally delete the blog post
                postService.removeById(postId);
                return "post removed!!";

            }
            else {
                return "Un authorized access!!";
            }


        }
        else {
            return "Un Authenticated access!!!";
        }
    }

    public String addComment(String email, String tokenValue, String commentBody, Long postId) {
        if(authenticationService.authenticate(email,tokenValue)) {

            //figure out the post which we are commenting
            Post PostToBeCommented = postService.getPostById(postId);

            //we have to figure out the commentor
            User commentor = userRepo.findFirstByUserEmail(email);

            // functionally more than 1 comment can be made on a particular post

            Comment newComment = new Comment(null,commentBody,
                    LocalDateTime.now(), PostToBeCommented, commentor);

            commentService.addComment(newComment);

            return commentor.getUserHandle() + " commented on " + postId;


        }
        else {
            return "Un Authenticated access!!!";
        }

    }

    public List<String> getCommentByPostId(String email, String tokenValue, Long postId) {

            if(authenticationService.authenticate(email,tokenValue)) {

                Post post = postService.getPostById(postId);

                return commentService.getCommentsByPostId(post);

            }
            else {
                List<String> dummy=new ArrayList<>();
                dummy.add("Un Authenticated access!!!");
                return dummy;
            }

    }

    public String editCommentByCommentId(String email, String tokenValue, Long commentId, String newComment) {
        if(authenticationService.authenticate(email,tokenValue)) {

          return commentService.editCommentById(commentId,newComment);
        }
        else {
            return "Un Authenticated access!!!";
        }
    }

    public String deleteComment(String email, String tokenValue, Long commentId) {
        if(authenticationService.authenticate(email,tokenValue)) {

            commentService.deleteCommentByCommentId(commentId);
            return "deleted Successfully";

        }
        else {
            return "Un Authenticated access!!!";

        }

    }

    public String updatePostLocationByPostId(String email, String tokenValue, Long postId, String location) {
        if(authenticationService.authenticate(email,tokenValue)) {
            postService.updatePostLocationByPostId(postId,location);
            return "updated Successfully";

        }
        else {
            return "Un Authenticated access!!!";

        }
    }
}
