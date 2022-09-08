package com.example.finalprojectstoreapp.services.impl;

import com.example.finalprojectstoreapp.dtos.order.OrderDto;
import com.example.finalprojectstoreapp.dtos.order.OrderListDto;
import com.example.finalprojectstoreapp.dtos.order.OrderListItemDto;
import com.example.finalprojectstoreapp.mappers.OrderMapper;
import com.example.finalprojectstoreapp.models.Order;
import com.example.finalprojectstoreapp.models.OrderStatus;
import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.repositories.OrderRepository;
import com.example.finalprojectstoreapp.services.OrderService;
import com.example.finalprojectstoreapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productServiceImpl;

    @Override
    public OrderListDto listOrderItems(User user) {
        List<Order> orderList = orderRepository.findAllByUserOrderByCreatedDesc(user);
        List<OrderListItemDto> orderItems = new ArrayList<>();
        double totalCost = 0;
        for (Order order : orderList) {
            OrderListItemDto orderListItemDto = new OrderListItemDto(order);
            totalCost += orderListItemDto.getQuantity() * order.getProduct().getPrice().doubleValue();
            orderItems.add(orderListItemDto);
        }
        log.info("Get all user`s orders. Username: {}", user.getUsername());
        return new OrderListDto(orderItems, totalCost);
    }

    @Override
    public List<OrderDto> orderAllFinish(User user) {
        List<Order> orderList = orderRepository.findAllByUserOrderByCreatedDesc(user);
        orderList.forEach(order -> {
            order.setStatus(OrderStatus.FINISHED);
            orderRepository.save(order);
        });
        log.info("Change status order to finish. Username: {}", user.getUsername());
        return orderList.stream()
                .map(OrderMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> orderAllCancel(User user) {
        List<Order> orderList = orderRepository.findAllByUserOrderByCreatedDesc(user);
        orderList.forEach(order -> {
            order.setStatus(OrderStatus.CANCELED);
            productServiceImpl.changeAvailable(order.getProduct().getId(), order.getQuantity(), true);
            orderRepository.save(order);
        });
        log.info("Change status order to cancel. Username: {}", user.getUsername());
        return orderList.stream()
                .map(OrderMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDto orderCancel(User user, Long orderId) {
        Order order = orderRepository.findByUserAndId(user, orderId);
        order.setStatus(OrderStatus.CANCELED);
        productServiceImpl.changeAvailable(order.getProduct().getId(), order.getQuantity(), true);
        orderRepository.save(order);
        log.info("Order with id {} deleted. Username: {}", orderId, user.getUsername());
        return OrderMapper.convertToDTO(order);
    }

    @Override
    public OrderDto orderFinish(User user, Long orderId) {
        Order order = orderRepository.findByUserAndId(user, orderId);
        order.setStatus(OrderStatus.FINISHED);
        orderRepository.save(order);
        log.info("Order with id {} finished. Username: {}", orderId, user.getUsername());
        return OrderMapper.convertToDTO(order);
    }

    @Override
    public OrderDto orderFinish(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(OrderStatus.FINISHED);
        orderRepository.save(order);
        log.info("Order with id {} finished", orderId);
        return OrderMapper.convertToDTO(order);
    }

    @Override
    public OrderDto orderCancel(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
        log.info("Order with id {} canceled", orderId);
        return OrderMapper.convertToDTO(order);
    }
}