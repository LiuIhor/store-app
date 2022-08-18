package com.example.finalprojectstoreapp.services;

import com.example.finalprojectstoreapp.dtos.cart.CartDto;
import com.example.finalprojectstoreapp.dtos.cart.CartListDto;
import com.example.finalprojectstoreapp.dtos.cart.CartListItemDto;
import com.example.finalprojectstoreapp.exceptions.CustomException;
import com.example.finalprojectstoreapp.mappers.CartMapper;
import com.example.finalprojectstoreapp.models.*;
import com.example.finalprojectstoreapp.repositories.CartRepository;
import com.example.finalprojectstoreapp.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productService;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;

    public CartDto addToCart(CartDto cartDto, User user) {
        Product product = productService.findById(cartDto.getProductId());

        Cart cart = Cart.builder()
                .product(product)
                .user(user)
                .quantity(cartDto.getQuantity())
                .created(new Date())
                .build();
        return CartMapper.convertToDTO(cartRepository.save(cart));
    }

    public CartListDto listCartItems(User user) {
        List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDesc(user);
        List<CartListItemDto> cartItems = new ArrayList<>();
        double totalCost = 0;
        for (Cart cart : cartList) {
            CartListItemDto cartListItemDto = new CartListItemDto(cart);
            totalCost += cartListItemDto.getQuantity() * cart.getProduct().getPrice().doubleValue();
            cartItems.add(cartListItemDto);
        }

        return new CartListDto(cartItems, totalCost);
    }

    public void deleteCart(Long cartId, User user) {
        Optional<Cart> optionalCart = cartRepository.findById(cartId);

        if (optionalCart.isEmpty()) {
            throw new CustomException("Cart is invalid: " + cartId);
        }

        Cart cart = optionalCart.get();

        if (cart.getUser() != user) {
            throw new CustomException("Cart item does not belong to user: " + cartId);
        }
        cartRepository.delete(cart);
    }

    public void checkout(User user) {
        cartRepository.findAllByUser(user)
                .forEach(cart -> {
                    Order order = new Order(cart);
                    order.setStatus(OrderStatus.NEW);
//                    cartRepository.delete(cart);
                    productService.decreaseAvailable(cart.getProduct().getId(), cart.getQuantity());
                    orderRepository.save(order);
                });
    }

    public CartDto updateCart(CartDto cartDto) throws Exception {
        if (!cartRepository.existsById(cartDto.getId())) {
            throw new Exception("Cart not present!");
        }
        Cart cart = CartMapper.convertToEntity(cartDto);
        return CartMapper
                .convertToDTO(cartRepository.save(cart));
    }
}