package com.peter.redditclonedemo.redditclonedemo.api.controller;

import com.peter.redditclonedemo.redditclonedemo.api.dto.AuthenticationResponse;
import com.peter.redditclonedemo.redditclonedemo.api.dto.LoginRequest;
import com.peter.redditclonedemo.redditclonedemo.api.dto.RegisterRequest;
import com.peter.redditclonedemo.redditclonedemo.api.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {
        authService.signup(registerRequest);
        return new ResponseEntity<>("User Registration Successful", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
        public ResponseEntity<String> verifyAccount(@PathVariable String token) {
        authService.verifyAccount(token);
        return new ResponseEntity<>("User Account Successfully Verified", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }
}
