package com.peter.redditclonedemo.redditclonedemo.api.mapper;

import com.peter.redditclonedemo.redditclonedemo.api.dto.SubredditDto;
import com.peter.redditclonedemo.redditclonedemo.api.model.Post;
import com.peter.redditclonedemo.redditclonedemo.api.model.Subreddit;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {return numberOfPosts.size();}

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
