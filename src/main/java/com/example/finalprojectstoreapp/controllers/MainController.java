package com.example.finalprojectstoreapp.controllers;

import com.example.finalprojectstoreapp.services.AuthenticationService;
import com.example.finalprojectstoreapp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final OrderService orderService;
    private final AuthenticationService authenticationService;

    @RequestMapping("/")
    public String doc() {
        return "redirect:swagger-ui/index.html#";
    }
}