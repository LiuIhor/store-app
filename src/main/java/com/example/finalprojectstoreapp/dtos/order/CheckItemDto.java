package com.example.finalprojectstoreapp.dtos.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckItemDto {
    private String productName;
    private int quantity;
    private double price;
    private long productId;
    private long userId;
}