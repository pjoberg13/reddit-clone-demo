package com.peter.redditclonedemo.redditclonedemo.api.repository;

import com.peter.redditclonedemo.redditclonedemo.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
