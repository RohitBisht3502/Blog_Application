package com.geekster.BloggingAPI.service;

import com.geekster.BloggingAPI.model.Post;
import com.geekster.BloggingAPI.repo.IPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    @Autowired
    IPostRepo postRepo;


    public void createPost(Post blogPost) {
        postRepo.save(blogPost);
    }


    public void removeById(Long postId) {
        postRepo.deleteById(postId);
    }


    public Post getPostById(Long postId) {
        return postRepo.findById(postId).orElseThrow();
    }

    public void updatePostLocationByPostId(Long postId,String location) {
        Post post=postRepo.findById(postId).orElseThrow();
        post.setPostLocation(location);
        postRepo.save(post);
    }
}
