package com.peter.redditclonedemo.redditclonedemo.api.service;

import com.peter.redditclonedemo.redditclonedemo.api.dto.LoginRequest;
import com.peter.redditclonedemo.redditclonedemo.api.dto.RegisterRequest;
import com.peter.redditclonedemo.redditclonedemo.api.exceptions.SpringRedditDemoException;
import com.peter.redditclonedemo.redditclonedemo.api.model.NotificationEmail;
import com.peter.redditclonedemo.redditclonedemo.api.model.User;
import com.peter.redditclonedemo.redditclonedemo.api.model.VerificationToken;
import com.peter.redditclonedemo.redditclonedemo.api.repository.UserRepository;
import com.peter.redditclonedemo.redditclonedemo.api.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated(Instant.now());
        user.setEnabled(false);
        userRepository.save(user);
        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Activate you account", user.getEmail(),
                "Click this link to complete your account verification " +
                        "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String newToken = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(newToken);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return newToken;
    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new SpringRedditDemoException("Invalid Token"));
        fetchUserAndEnable(verificationToken.get());
    }

    @Transactional
    private void fetchUserAndEnable(VerificationToken verificationToken) {
        String username = verificationToken.getUser().getUsername();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditDemoException("User not found with name " + username));
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
    }
}
