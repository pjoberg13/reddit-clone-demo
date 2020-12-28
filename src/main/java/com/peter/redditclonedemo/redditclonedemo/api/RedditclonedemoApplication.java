package com.peter.redditclonedemo.redditclonedemo.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class RedditclonedemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditclonedemoApplication.class, args);
	}

}
