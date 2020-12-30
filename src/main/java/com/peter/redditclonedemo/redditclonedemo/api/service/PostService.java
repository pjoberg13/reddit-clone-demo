package com.peter.redditclonedemo.redditclonedemo.api.service;

import com.peter.redditclonedemo.redditclonedemo.api.dto.PostRequest;
import com.peter.redditclonedemo.redditclonedemo.api.dto.PostResponse;
import com.peter.redditclonedemo.redditclonedemo.api.exceptions.PostNotFoundException;
import com.peter.redditclonedemo.redditclonedemo.api.exceptions.SubredditNotFoundException;
import com.peter.redditclonedemo.redditclonedemo.api.mapper.PostMapper;
import com.peter.redditclonedemo.redditclonedemo.api.model.Post;
import com.peter.redditclonedemo.redditclonedemo.api.model.Subreddit;
import com.peter.redditclonedemo.redditclonedemo.api.model.User;
import com.peter.redditclonedemo.redditclonedemo.api.repository.PostRepository;
import com.peter.redditclonedemo.redditclonedemo.api.repository.SubredditRepository;
import com.peter.redditclonedemo.redditclonedemo.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
@Transactional
public class PostService {

    private final SubredditRepository subredditRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AuthService authService;
    private final UserRepository userRepository;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
                .orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));

        Post savedPost = postRepository.save(postMapper.map(postRequest, subreddit, authService.getCurrentUser()));
        subreddit.getPosts().add(savedPost);
        subredditRepository.save(subreddit);
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id.toString()));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsBySubreddit(Long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId)
                .orElseThrow(() -> new SubredditNotFoundException(subredditId.toString()));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(postMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
