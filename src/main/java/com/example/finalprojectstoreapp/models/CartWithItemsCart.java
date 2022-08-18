package com.example.finalprojectstoreapp.models;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

//@Component
@SessionScope
public class CartWithItemsCart {
    List<Cart> carts;

    public CartWithItemsCart() {
        carts = new ArrayList<>();
    }

    public Cart save(Cart cart) {
        carts.add(cart);
        return cart;
    }

    public List<Cart> findAllByUser(User user) {
        if (carts.isEmpty()) {
            return null;
        }
        return carts.stream()
                .filter(cart -> cart.getUser().equals(user)).
                collect(Collectors.toList());
    }

    public Cart findById(Long cartId) {

        for (Cart cart : carts) {
            if (cart.getId().equals(cartId)) {
                return cart;
            }
        }
        return null;
    }

    public void delete(Cart cart) {
        carts.remove(cart);
    }

   public boolean exists(Cart cart) {
        return carts.contains(cart);
   }
}
