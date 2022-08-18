package com.example.finalprojectstoreapp.services;

import com.example.finalprojectstoreapp.exceptions.AuthenticationFailException;
import com.example.finalprojectstoreapp.models.AuthenticationToken;
import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.repositories.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final TokenRepository tokenRepository;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    public User getUserByToken(String token) {
        AuthenticationToken authenticationToken = tokenRepository.findByToken(token);

        if (Objects.isNull(authenticationToken)) {
            return null;
        }
        return authenticationToken.getUser();
    }

    public void authenticate(String token) {
        if (Objects.isNull(token)) {
            throw new AuthenticationFailException("Token not present!");
        }

        if (Objects.isNull(getUserByToken(token))) {
            throw new AuthenticationFailException("Token not valid");
        }
    }
}