package com.example.finalprojectstoreapp.dtos.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {

    private Long id;
    @NotNull
    private Long productId;
    @NotNull
    private Integer quantity;

}
