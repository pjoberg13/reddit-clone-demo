package com.peter.redditclonedemo.redditclonedemo.api.dto;

import com.peter.redditclonedemo.redditclonedemo.api.model.VoteType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VoteDto {
    private VoteType voteType;
    private Long postId;
}
