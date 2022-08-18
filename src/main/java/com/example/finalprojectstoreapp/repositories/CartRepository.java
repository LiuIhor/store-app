package com.example.finalprojectstoreapp.repositories;

import com.example.finalprojectstoreapp.models.Cart;
import com.example.finalprojectstoreapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserOrderByCreatedDesc(User user);

    List<Cart> findAllByUser(User user);
}