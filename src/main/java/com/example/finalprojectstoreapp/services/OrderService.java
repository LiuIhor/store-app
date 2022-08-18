package com.example.finalprojectstoreapp.services;

import com.example.finalprojectstoreapp.dtos.OrderDto;
import com.example.finalprojectstoreapp.dtos.order.OrderListDto;
import com.example.finalprojectstoreapp.dtos.order.OrderListItemDto;
import com.example.finalprojectstoreapp.mappers.OrderMapper;
import com.example.finalprojectstoreapp.models.Order;
import com.example.finalprojectstoreapp.models.OrderStatus;
import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderListDto listOrderItems(User user) {
        List<Order> orderList = orderRepository.findAllByUserOrderByCreatedDesc(user);
        List<OrderListItemDto> orderItems = new ArrayList<>();
        double totalCost = 0;
        for (Order order : orderList) {
            OrderListItemDto orderListItemDto = new OrderListItemDto(order);
            totalCost += orderListItemDto.getQuantity() * order.getProduct().getPrice().doubleValue();
            orderItems.add(orderListItemDto);
        }

        return new OrderListDto(orderItems, totalCost);
    }

    public List<OrderDto> orderAllFinish(User user) {
        List<Order> orderList = orderRepository.findAllByUserOrderByCreatedDesc(user);

        orderList.forEach(order -> {
            order.setStatus(OrderStatus.FINISHED);
            orderRepository.save(order);
        });
        return orderList.stream()
                .map(OrderMapper::convertToDTO).collect(Collectors.toList());
    }

    public List<OrderDto> orderAllCancel(User user) {
        List<Order> orderList = orderRepository.findAllByUserOrderByCreatedDesc(user);
        orderList.forEach(order -> {
            order.setStatus(OrderStatus.CANCELED);
            productService.increaseAvailable(order.getProduct().getId(), order.getQuantity());
            orderRepository.save(order);
        });
        return orderList.stream()
                .map(OrderMapper::convertToDTO).collect(Collectors.toList());
    }

    public OrderDto orderCancel(User user, Long orderId) {
        Order order = orderRepository.findByUserAndId(user, orderId);
        order.setStatus(OrderStatus.CANCELED);
        productService.increaseAvailable(order.getProduct().getId(), order.getQuantity());
        orderRepository.save(order);
        return OrderMapper.convertToDTO(order);
    }

    public OrderDto orderFinish(User user, Long orderId) {
        Order order = orderRepository.findByUserAndId(user, orderId);
        order.setStatus(OrderStatus.FINISHED);
        orderRepository.save(order);
        return OrderMapper.convertToDTO(order);
    }
}