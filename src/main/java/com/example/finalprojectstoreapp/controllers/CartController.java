package com.example.finalprojectstoreapp.controllers;

import com.example.finalprojectstoreapp.dtos.cart.CartDto;
import com.example.finalprojectstoreapp.dtos.cart.CartListDto;
import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.services.AuthenticationService;
import com.example.finalprojectstoreapp.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final AuthenticationService authenticationService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CartDto addToCart(@RequestBody CartDto cartDto,
                             @RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUserByToken(token);

        return cartService.addToCart(cartDto, user);
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public CartListDto getCartItems(@RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUserByToken(token);
        return cartService.listCartItems(user);
    }

    @PutMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public CartDto updeteCart(@PathVariable("cartId") Long cartId, @RequestBody CartDto cartDto) throws Exception {
        cartDto.setId(cartId);
        return cartService.updateCart(cartDto);
    }

    @DeleteMapping("/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCartById(@PathVariable("cartId") Long cartId,
                               @RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUserByToken(token);

        cartService.deleteCart(cartId, user);
    }

    @PostMapping("/checkout")
    public void checkList(@RequestParam("token") String token) {
        authenticationService.authenticate(token);

        User user = authenticationService.getUserByToken(token);
        cartService.checkout(user);
    }
}
