package com.peter.redditclonedemo.redditclonedemo.api.repository;

import com.peter.redditclonedemo.redditclonedemo.api.model.Post;
import com.peter.redditclonedemo.redditclonedemo.api.model.Subreddit;
import com.peter.redditclonedemo.redditclonedemo.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
