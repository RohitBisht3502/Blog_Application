package com.geekster.BloggingAPI.repo;

import com.geekster.BloggingAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  IUserRepo extends JpaRepository<User,Long> {
    User findFirstByUserEmail(String newEmail);
}
