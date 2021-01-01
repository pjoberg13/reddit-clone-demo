package com.peter.redditclonedemo.redditclonedemo.api.repository;

import com.peter.redditclonedemo.redditclonedemo.api.model.Comment;
import com.peter.redditclonedemo.redditclonedemo.api.model.Post;
import com.peter.redditclonedemo.redditclonedemo.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
