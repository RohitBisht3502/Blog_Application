package com.geekster.BloggingAPI.repo;

import com.geekster.BloggingAPI.model.Comment;
import com.geekster.BloggingAPI.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICommentRepo extends JpaRepository<Comment,Long> {
    List<Comment> findCommentByPost(Post post);
}
