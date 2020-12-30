package com.peter.redditclonedemo.redditclonedemo.api.exceptions;

public class SubredditNotFoundException extends RuntimeException{

    public SubredditNotFoundException(String message) {
        super(message);
    }

}
