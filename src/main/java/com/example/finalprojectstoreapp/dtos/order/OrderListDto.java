package com.example.finalprojectstoreapp.dtos.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderListDto {

    private List<OrderListItemDto> orderListItemListDto;
    private double totalCost;
}