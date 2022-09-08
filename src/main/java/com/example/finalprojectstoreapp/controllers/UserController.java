package com.example.finalprojectstoreapp.controllers;

import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userServiceImpl;

    /**
     * Endpoint to get all users
     *
     * @return List of users
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        log.info("Getting all users");
        return userServiceImpl.getAllUsers();
    }
}
