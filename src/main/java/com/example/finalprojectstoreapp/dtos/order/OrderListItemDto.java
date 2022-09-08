package com.example.finalprojectstoreapp.dtos.order;

import com.example.finalprojectstoreapp.models.Order;
import com.example.finalprojectstoreapp.models.OrderStatus;
import com.example.finalprojectstoreapp.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListItemDto {

    private Long id;
    private Integer quantity;
    private Product product;
    private OrderStatus orderStatus;
    private LocalDateTime created;
    private LocalDateTime updated;

    public OrderListItemDto(Order order) {
        this.id = order.getId();
        this.quantity = order.getQuantity();
        this.product = order.getProduct();
        this.orderStatus = order.getStatus();
        this.created = order.getCreated();
        this.updated = order.getUpdated();
    }
}