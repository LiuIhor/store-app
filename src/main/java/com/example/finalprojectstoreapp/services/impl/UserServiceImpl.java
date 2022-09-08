package com.example.finalprojectstoreapp.services.impl;

import com.example.finalprojectstoreapp.exceptions.CustomException;
import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.repositories.UserRepository;
import com.example.finalprojectstoreapp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByUsername(String username) {
        log.info("Got user with username: {}", username);
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(String.format("User with username {} not found", username)));
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Got all users");
        return userRepository.findAll();
    }
}