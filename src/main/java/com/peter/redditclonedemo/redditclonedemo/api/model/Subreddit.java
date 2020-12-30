package com.peter.redditclonedemo.redditclonedemo.api.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Subreddit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subredditId;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @OneToMany(fetch = FetchType.LAZY)
    private List<Post> posts;
    private Instant createdDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
