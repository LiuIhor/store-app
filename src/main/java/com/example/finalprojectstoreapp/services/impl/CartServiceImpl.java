package com.example.finalprojectstoreapp.services.impl;

import com.example.finalprojectstoreapp.dtos.cart.CartDto;
import com.example.finalprojectstoreapp.dtos.cart.CartListDto;
import com.example.finalprojectstoreapp.dtos.cart.CartListItemDto;
import com.example.finalprojectstoreapp.exceptions.CustomException;
import com.example.finalprojectstoreapp.mappers.CartMapper;
import com.example.finalprojectstoreapp.models.*;
import com.example.finalprojectstoreapp.repositories.CartRepository;
import com.example.finalprojectstoreapp.repositories.OrderRepository;
import com.example.finalprojectstoreapp.services.CartService;
import com.example.finalprojectstoreapp.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class CartServiceImpl implements CartService {

    private final ProductService productServiceImpl;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    @Override
    public CartDto addToCart(CartDto cartDto, User user) {
        Product product = productServiceImpl.findById(cartDto.getProductId());
        log.info("Item added to cart. Username: {}", user.getUsername());
        Cart cart = Cart.builder()
                .product(product)
                .user(user)
                .quantity(cartDto.getQuantity())
                .created(new Date())
                .build();
        return CartMapper.convertToDTO(cartRepository.save(cart));
    }

    @Override
    public CartListDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDesc(user);
        List<CartListItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartList) {
            CartListItemDto cartListItemDto = new CartListItemDto(cart);
            totalCost += cartListItemDto.getQuantity() * cart.getProduct().getPrice().doubleValue();
            cartItems.add(cartListItemDto);
        }
        log.info("Get list items. Username: {}", user.getUsername());
        return new CartListDto(cartItems, totalCost);
    }

    @Override
    public void deleteCart(Long cartId, User user) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);
        if (optionalCart.isEmpty()) {
            throw new CustomException("Cart is invalid: " + cartId);
        }
        Cart cart = optionalCart.get();
        if (cart.getUser() != user) {
            throw new CustomException("Cart item does not belong to user: " + cartId);
        }
        log.info("Delete item from cart. Username: {}", user.getUsername());
        cartRepository.delete(cart);
    }

    @Override
    public void checkout(User user) {
        log.info("Check user`s cart. Username: {}", user.getUsername());
        cartRepository.findAllByUser(user)
                .forEach(cart -> {
                    Order order = new Order(cart);
                    order.setStatus(OrderStatus.NEW);
                    productServiceImpl.changeAvailable(cart.getProduct().getId(), cart.getQuantity(), false);
                    cartRepository.delete(cart);
                    orderRepository.save(order);
                });
    }

    @Override
    public CartDto updateCart(CartDto cartDto) throws CustomException {
        if (!cartRepository.existsById(cartDto.getId())) {
            throw new CustomException("Cart not present!");
        }
        Cart cart = CartMapper.convertToEntity(cartDto);
        log.info("Update user`s cart. ");
        return CartMapper
                .convertToDTO(cartRepository.save(cart));
    }
}