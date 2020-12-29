package com.peter.redditclonedemo.redditclonedemo.api.service;

import com.peter.redditclonedemo.redditclonedemo.api.dto.SubredditDto;
import com.peter.redditclonedemo.redditclonedemo.api.model.Subreddit;
import com.peter.redditclonedemo.redditclonedemo.api.repository.SubredditRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class SubredditService {

    private final SubredditRepository subredditRepository;

    @Transactional
    public SubredditDto save(SubredditDto subredditDto) {
        Subreddit save = subredditRepository.save(mapSubreddittoDto(subredditDto));
        subredditDto.setId(save.getSubredditId());
        return subredditDto;
    }

    private Subreddit mapSubreddittoDto(SubredditDto subredditDto) {
        return Subreddit.builder().name(subredditDto.getName())
                .description(subredditDto.getDescription())
                .build();
    }

    @Transactional(readOnly = true)
    public List<SubredditDto> getAll() {
        return subredditRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private SubredditDto mapToDto(Subreddit subreddit) {
        return SubredditDto.builder()
                .name(subreddit.getName())
                .id(subreddit.getSubredditId())
                .numberOfPosts(subreddit.getPosts().size())
                .build();
    }
}
