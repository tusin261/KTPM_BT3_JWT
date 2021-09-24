package com.example.jwt_nguyenthanhtu_18045511.repository;

import com.example.jwt_nguyenthanhtu_18045511.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
