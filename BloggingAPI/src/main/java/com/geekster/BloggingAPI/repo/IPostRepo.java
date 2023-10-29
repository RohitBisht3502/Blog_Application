package com.geekster.BloggingAPI.repo;

import com.geekster.BloggingAPI.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepo extends JpaRepository<Post,Long> {
}
