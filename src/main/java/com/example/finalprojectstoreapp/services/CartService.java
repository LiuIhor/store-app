package com.example.finalprojectstoreapp.services;

import com.example.finalprojectstoreapp.dtos.cart.CartDto;
import com.example.finalprojectstoreapp.dtos.cart.CartListDto;
import com.example.finalprojectstoreapp.exceptions.CustomException;
import com.example.finalprojectstoreapp.models.User;

public interface CartService {

    /**
     * Method to add product to cart
     *
     * @param cartDto Item in cart
     * @param user    Cart owner
     * @return added item from cart
     */
    CartDto addToCart(CartDto cartDto, User user);

    /**
     * Method to get all items in cart
     *
     * @param user Cart owner
     * @return List of items
     */
    CartListDto listCartItems(User user);

    /**
     * Method to delete item from cart
     *
     * @param cartId Id item in cart
     * @param user   Cart owner
     */
    void deleteCart(Long cartId, User user);

    /**
     * Method to check cart
     *
     * @param user Cart owner
     */
    void checkout(User user);

    /**
     * Method to update cart
     *
     * @param cartDto Updated cart
     * @return New updated cart
     * @throws CustomException If cart not present
     */
    CartDto updateCart(CartDto cartDto);
}