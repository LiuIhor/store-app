package com.example.finalprojectstoreapp.controllers;

import com.example.finalprojectstoreapp.dtos.cart.CartDto;
import com.example.finalprojectstoreapp.dtos.cart.CartListDto;
import com.example.finalprojectstoreapp.models.User;
import com.example.finalprojectstoreapp.security.configs.jwt.JwtUtils;
import com.example.finalprojectstoreapp.services.CartService;
import com.example.finalprojectstoreapp.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/carts")
@RequiredArgsConstructor
@Log4j2
public class CartController {

    private final CartService cartServiceImpl;
    private final UserService userServiceImpl;
    private final JwtUtils jwtUtils;

    /**
     * Endpoint to add product to user`s cart
     *
     * @param cartDto - DTO product with field: productId, quantity
     * @param request - Needed to get token from the header
     * @return CartDto - Product that was added to the cart
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('CLIENT')")
    public CartDto addToCart(@RequestBody CartDto cartDto,
                             HttpServletRequest request) {
        String username = jwtUtils.getUsernameByRequestToken(request);
        User user = userServiceImpl.getUserByUsername(username);
        log.info("Adding an item to the shopping cart of a user with a username: {}", username);
        return cartServiceImpl.addToCart(cartDto, user);
    }

    /**
     * Endpoint to get products from user`s cart
     *
     * @param request Needed to get token from the header
     * @return CartListDto - Cart product list
     */
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CLIENT')")
    public CartListDto getCartItems(HttpServletRequest request) {
        String username = jwtUtils.getUsernameByRequestToken(request);
        User user = userServiceImpl.getUserByUsername(username);
        log.info("Getting the cart of the user with the username: {}", username);
        return cartServiceImpl.listCartItems(user);
    }

    /**
     * Endpoint to change product from user`s cart
     *
     * @param cartId  - Id of the cart to be change
     * @param cartDto - Changed product from user`s cart
     * @return Changed product from user`s cart
     * @throws Exception if Cart not present!
     */
    @PutMapping("/{cartId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('CLIENT')")
    public CartDto updateCart(@PathVariable("cartId") Long cartId, @RequestBody CartDto cartDto) {
        cartDto.setId(cartId);
        log.info("Cart item will be  changed to {}", cartDto);
        return cartServiceImpl.updateCart(cartDto);
    }

    /**
     * Endpoint to delete product from user`s cart
     *
     * @param cartId  Id of item cart to be deleted
     * @param request Needed to get token from the header
     */
    @DeleteMapping("/{cartId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('CLIENT')")
    public void deleteCartById(@PathVariable("cartId") Long cartId,
                               HttpServletRequest request) {
        String username = jwtUtils.getUsernameByRequestToken(request);
        User user = userServiceImpl.getUserByUsername(username);
        log.info("Cart item with id {} will be  deleted. Username: {}", cartId, username);
        cartServiceImpl.deleteCart(cartId, user);
    }

    /**
     * Endpoint to check user`s cart
     *
     * @param request Needed to get token from the header
     */
    @PostMapping("/checkout")
    @PreAuthorize("hasRole('CLIENT')")
    public void checkList(
            HttpServletRequest request) {
        String username = jwtUtils.getUsernameByRequestToken(request);
        User user = userServiceImpl.getUserByUsername(username);
        log.info("User`s cart will be checked. Username: {}", username);
        cartServiceImpl.checkout(user);
    }
}