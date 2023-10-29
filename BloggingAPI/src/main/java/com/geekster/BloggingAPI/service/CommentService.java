package com.geekster.BloggingAPI.service;

import com.geekster.BloggingAPI.model.Comment;
import com.geekster.BloggingAPI.model.Post;
import com.geekster.BloggingAPI.repo.ICommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    ICommentRepo commentRepo;

    public void addComment(Comment newComment) {
        commentRepo.save(newComment);
    }

    public List<String> getCommentsByPostId(Post post) {
        List<Comment> comments = commentRepo.findCommentByPost(post);
        List<String> commentsDesc = new ArrayList<>();
        for (Comment comment : comments) {
            commentsDesc.add(comment.getCommentBody()); // Assuming "getBody()" is the method to retrieve the comment body
        }
        return commentsDesc;
    }



    public String editCommentById(Long commentId,String newComment) {
       Comment comment= commentRepo.findById(commentId).orElseThrow();
       comment.setCommentBody(newComment);
       commentRepo.save(comment);
       return "edited successfully";
    }

    public void deleteCommentByCommentId(Long commentId) {
        commentRepo.deleteById(commentId);
    }
}
