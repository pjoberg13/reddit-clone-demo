package com.peter.redditclonedemo.redditclonedemo.api.controller;

import com.peter.redditclonedemo.redditclonedemo.api.dto.PostRequest;
import com.peter.redditclonedemo.redditclonedemo.api.dto.PostResponse;
import com.peter.redditclonedemo.redditclonedemo.api.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.status;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long postId) {
        return status(HttpStatus.OK).body(postService.getPost(postId));
    }

    @GetMapping("/")
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @GetMapping("/by-subreddit/{subredditId}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(Long subredditId) {
        return status(HttpStatus.OK).body(postService.getPostsBySubreddit(subredditId));
    }

    @GetMapping("/by-user/{name}")
    public ResponseEntity<List<PostResponse>> getPostsByUsername(String username) {
        return status(HttpStatus.OK).body(postService.getPostsByUsername(username));
    }
}
