package com.example.finalprojectstoreapp.repositories;

import com.example.finalprojectstoreapp.models.Order;
import com.example.finalprojectstoreapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserOrderByCreatedDesc(User user);

    Order findByUserAndId(User user, Long orderId);

    List<Order> findAllByUser(User user);
}