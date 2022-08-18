package com.example.finalprojectstoreapp.dtos.cart;

import com.example.finalprojectstoreapp.models.Cart;
import com.example.finalprojectstoreapp.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartListItemDto {
    private  Long id;
    private Integer quantity;
    private Product product;

    public CartListItemDto(Cart cart) {
        this.id = cart.getId();
        this.quantity = cart.getQuantity();
        this.product = cart.getProduct();
    }
}
