package com.example.finalprojectstoreapp.mappers;

import com.example.finalprojectstoreapp.dtos.cart.CartDto;
import com.example.finalprojectstoreapp.models.Cart;
import lombok.experimental.UtilityClass;
import org.modelmapper.ModelMapper;

@UtilityClass
public class CartMapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public Cart convertToEntity(CartDto cartDto) {
        return modelMapper.map(cartDto, Cart.class);
    }

    public CartDto convertToDTO(Cart cart) {
        return modelMapper.map(cart, CartDto.class);
    }
}