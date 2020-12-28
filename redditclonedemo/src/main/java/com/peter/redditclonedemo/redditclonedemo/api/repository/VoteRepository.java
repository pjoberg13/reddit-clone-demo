package com.peter.redditclonedemo.redditclonedemo.api.repository;

import com.peter.redditclonedemo.redditclonedemo.api.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
