package com.example.finalprojectstoreapp.services;

import com.example.finalprojectstoreapp.models.User;

import java.util.List;

public interface UserService {

    /**
     * Method to get user by username
     *
     * @param username - User`s username
     * @return Found user
     */
    User getUserByUsername(String username);

    /**
     * Method to find all users
     *
     * @return List of users
     */
    List<User> getAllUsers();
}