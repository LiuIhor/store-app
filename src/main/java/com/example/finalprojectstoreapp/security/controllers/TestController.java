package com.example.finalprojectstoreapp.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/all")
    public String allAccess() {
        return "public API";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('CLIENT') or hasRole('MANAGER') or hasRole('ADMIN')")
    public String userAccess() {
        return "user API";
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public String moderatorAccess() {
        return "moderator API";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "admin API";
    }
}