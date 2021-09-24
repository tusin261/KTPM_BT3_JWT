package com.example.jwt_nguyenthanhtu_18045511.repository;

import com.example.jwt_nguyenthanhtu_18045511.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
    Token findByToken(String token);
}
