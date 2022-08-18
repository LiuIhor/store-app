package com.example.finalprojectstoreapp.controllers;

import com.example.finalprojectstoreapp.dtos.SignInDto;
import com.example.finalprojectstoreapp.dtos.SignInResponseDto;
import com.example.finalprojectstoreapp.dtos.SignUpResponseDto;
import com.example.finalprojectstoreapp.dtos.SignUpDto;
import com.example.finalprojectstoreapp.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpDto signupDto) {
        System.out.println(signupDto);
        return userService.signUp(signupDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto) {
        return  userService.signIn(signInDto);
    }
}
