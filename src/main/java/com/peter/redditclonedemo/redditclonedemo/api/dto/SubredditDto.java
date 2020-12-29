package com.peter.redditclonedemo.redditclonedemo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class SubredditDto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;

}
