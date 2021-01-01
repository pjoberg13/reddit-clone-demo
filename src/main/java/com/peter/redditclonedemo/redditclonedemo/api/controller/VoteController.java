package com.peter.redditclonedemo.redditclonedemo.api.controller;

import com.peter.redditclonedemo.redditclonedemo.api.dto.VoteDto;
import com.peter.redditclonedemo.redditclonedemo.api.service.VoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RequestMapping("/api/votes/")
@RestController
public class VoteController {

    private final VoteService voteService;

    @PostMapping
    public ResponseEntity vote(@RequestBody VoteDto voteDto) {
        voteService.vote(voteDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
