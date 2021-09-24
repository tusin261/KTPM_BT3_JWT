package com.example.jwt_nguyenthanhtu_18045511.service;

import com.example.jwt_nguyenthanhtu_18045511.authen.UserPrincipal;
import com.example.jwt_nguyenthanhtu_18045511.entity.User;

public interface UserService {
    User createUser(User user);
    UserPrincipal findByUsername(String username);
}
