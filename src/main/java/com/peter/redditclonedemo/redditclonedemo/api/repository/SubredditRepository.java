package com.peter.redditclonedemo.redditclonedemo.api.repository;

import com.peter.redditclonedemo.redditclonedemo.api.model.Subreddit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubredditRepository extends JpaRepository<Subreddit, Long> {

    Optional<Subreddit> findByName(String subredditName);
}
