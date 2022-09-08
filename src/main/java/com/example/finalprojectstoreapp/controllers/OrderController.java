package com.example.finalprojectstoreapp.controllers;

import com.example.finalprojectstoreapp.dtos.order.OrderDto;
import com.example.finalprojectstoreapp.dtos.order.OrderListDto;
import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.security.configs.jwt.JwtUtils;
import com.example.finalprojectstoreapp.services.OrderService;
import com.example.finalprojectstoreapp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
@Log4j2
public class OrderController {

    private final OrderService orderServiceImpl;
    private final UserService userServiceImpl;
    private final JwtUtils jwtUtils;

    /**
     * Endpoint to get all Items from order
     *
     * @param request Needed to get token from the header
     * @return List of Items from order
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CLIENT')")
    public OrderListDto getOrderItems(HttpServletRequest request) {
        String username = jwtUtils.getUsernameByRequestToken(request);
        User user = userServiceImpl.getUserByUsername(username);
        log.info("Getting the orders of the user with the username: {}", username);
        return orderServiceImpl.listOrderItems(user);
    }

    /**
     * Endpoint to change status order to CANCEL
     *
     * @param orderId - Order with this id will be changed
     * @param request Needed to get token from the header
     * @return Changed order to CANCEL
     */
    @PatchMapping("/cancel/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CLIENT') or hasRole('MANAGER')")
    public OrderDto orderCancel(@PathVariable("orderId") Long orderId,
                                HttpServletRequest request) {
        String username = jwtUtils.getUsernameByRequestToken(request);
        User user = userServiceImpl.getUserByUsername(username);
        if (user.getRoles().contains("MANAGER")) {
            log.info("Order with id {} will be changed Manager ", orderId);
            return orderServiceImpl.orderCancel(orderId);
        } else {
            log.info("Order with id {} will be changed Client. Username: {} ", orderId, username);
            return orderServiceImpl.orderCancel(user, orderId);
        }
    }

    /**
     * Endpoint to change status order to FiNISH
     *
     * @param orderId - Order with this id will be changed
     * @param request Needed to get token from the header
     * @return Changed order to FINISH
     */
    @PatchMapping("/finish/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CLIENT') or hasRole('MANAGER')")
    public OrderDto orderFinish(@PathVariable("orderId") Long orderId,
                                HttpServletRequest request) {
        String username = jwtUtils.getUsernameByRequestToken(request);
        User user = userServiceImpl.getUserByUsername(username);
        if (user.getRoles().contains("MANAGER")) {
            log.info("Order with id {} will be changed Manager ", orderId);
            return orderServiceImpl.orderFinish(orderId);
        } else {
            log.info("Order with id {} will be changed Client. Username: {} ", orderId, username);
            return orderServiceImpl.orderFinish(user, orderId);
        }
    }
}