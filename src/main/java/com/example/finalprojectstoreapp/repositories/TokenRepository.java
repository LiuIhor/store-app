package com.example.finalprojectstoreapp.repositories;

import com.example.finalprojectstoreapp.models.AuthenticationToken;
import com.example.finalprojectstoreapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findByUser(User user);

    AuthenticationToken findByToken(String token);
}