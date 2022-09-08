package com.example.finalprojectstoreapp.dtos.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartListDto {

    private List<CartListItemDto> cartListItemListDto;
    private double totalCost;
}
