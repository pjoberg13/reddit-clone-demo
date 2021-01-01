package com.peter.redditclonedemo.redditclonedemo.api.service;

import com.peter.redditclonedemo.redditclonedemo.api.dto.CommentsDto;
import com.peter.redditclonedemo.redditclonedemo.api.exceptions.PostNotFoundException;
import com.peter.redditclonedemo.redditclonedemo.api.mapper.CommentMapper;
import com.peter.redditclonedemo.redditclonedemo.api.model.Comment;
import com.peter.redditclonedemo.redditclonedemo.api.model.NotificationEmail;
import com.peter.redditclonedemo.redditclonedemo.api.model.Post;
import com.peter.redditclonedemo.redditclonedemo.api.model.User;
import com.peter.redditclonedemo.redditclonedemo.api.repository.CommentRepository;
import com.peter.redditclonedemo.redditclonedemo.api.repository.PostRepository;
import com.peter.redditclonedemo.redditclonedemo.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;

    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(commentsDto.getPostId().toString()));
        Comment comment = commentMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        String message = mailContentBuilder.build(post.getUser().getUsername() + " commented on your post! See what they said.");
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendMail(new NotificationEmail(user.getUsername() + " left a comment on your post!", user.getEmail(), message));
    }

    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException(postId.toString()));
        return commentRepository.findByPost(post).stream()
                .map(commentMapper::mapToDto).collect(Collectors.toList());
    }

    public List<CommentsDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto).collect(Collectors.toList());
    }
}
