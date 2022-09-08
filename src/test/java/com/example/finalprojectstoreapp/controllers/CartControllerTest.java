package com.example.finalprojectstoreapp.controllers;

import com.example.finalprojectstoreapp.dtos.cart.CartDto;
import com.example.finalprojectstoreapp.dtos.cart.CartListDto;
import com.example.finalprojectstoreapp.dtos.cart.CartListItemDto;
import com.example.finalprojectstoreapp.mappers.CartMapper;
import com.example.finalprojectstoreapp.models.Cart;
import com.example.finalprojectstoreapp.models.Product;
import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.security.configs.jwt.JwtUtils;
import com.example.finalprojectstoreapp.services.CartService;
import com.example.finalprojectstoreapp.services.UserService;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock
    private CartService cartServiceImpl;

    @Mock
    private UserService userServiceImpl;

    @Mock
    private JwtUtils jwtUtils;

    @InjectMocks
    private CartController cartController;

    @Test
    void addToCart() {
        Cart cart = createCart();
        User user = createUser();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("Authorization", "token");
        when(jwtUtils.getUsernameByRequestToken(request)).thenReturn("username");
        when(userServiceImpl.getUserByUsername("username")).thenReturn(user);
        when(cartServiceImpl.addToCart(CartMapper.convertToDTO(cart), user))
                .thenReturn(CartMapper.convertToDTO(cart));
        CartDto actual = cartController.addToCart(CartMapper.convertToDTO(cart), request);
        assertEquals(CartMapper.convertToDTO(cart), actual);
        verify(cartServiceImpl, times(1)).addToCart(CartMapper.convertToDTO(cart), user);
    }

    @Test
    void getCartItems() {
        Cart cart = createCart();
        User user = createUser();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("Authorization", "token");
        when(jwtUtils.getUsernameByRequestToken(request)).thenReturn("username");
        when(userServiceImpl.getUserByUsername("username")).thenReturn(user);
        List<CartListItemDto> items = new ArrayList<>();
        items.add(new CartListItemDto(createCart()));
        when(cartServiceImpl.listCartItems(user))
                .thenReturn(new CartListDto(items, 700));
        CartListDto actual = cartController.getCartItems(request);
        assertEquals(new CartListDto(items, 700) ,actual);
        verify(cartServiceImpl, times(1)).addToCart(CartMapper.convertToDTO(cart), user);
    }

    @Test
    void updateCart() {
    }

    @Test
    void deleteCartById() {
    }

    @Test
    void checkList() {
    }

    Cart createCart() {
        return Cart.builder()
                .id(1L)
                .product(new Product(1L, "Phone", new BigDecimal(1200), 9))
                .user(createUser())
                .quantity(10)
                .build();
    }

    User createUser(){
        return User.builder()
                .id(1L)
                .username("username")
                .email("username@gmail.com")
                .password("username")
                .build();
    }
}