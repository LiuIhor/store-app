package com.example.finalprojectstoreapp.controllers;

import com.example.finalprojectstoreapp.dtos.OrderDto;
import com.example.finalprojectstoreapp.dtos.order.OrderListDto;
import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.services.AuthenticationService;
import com.example.finalprojectstoreapp.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final AuthenticationService authenticationService;
    private final OrderService orderService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public OrderListDto getOrderItems(@RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUserByToken(token);

        return orderService.listOrderItems(user);
    }

    @PatchMapping("/cancel/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto orderCancel(@PathVariable("orderId") Long orderId,
                                @RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUserByToken(token);
        return orderService.orderCancel(user, orderId);
    }

    @PatchMapping("/finish/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto orderFinish(@PathVariable("orderId") Long orderId,
                                @RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUserByToken(token);
        return orderService.orderFinish(user, orderId);
    }
}