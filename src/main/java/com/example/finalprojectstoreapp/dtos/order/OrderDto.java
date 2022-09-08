package com.example.finalprojectstoreapp.dtos.order;

import com.example.finalprojectstoreapp.models.OrderStatus;
import com.example.finalprojectstoreapp.models.Product;
import com.example.finalprojectstoreapp.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {

    private Long id;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private LocalDateTime updated;
    @NotNull
    private Product product;
    @NotNull
    private User user;
    @NotNull
    private Integer quantity;
    @NotNull
    private OrderStatus status;
}
