package com.example.jwt_nguyenthanhtu_18045511.service;

import com.example.jwt_nguyenthanhtu_18045511.entity.User;
import com.example.jwt_nguyenthanhtu_18045511.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User createUser(User user) {
        return repository.saveAndFlush(user);
    }
}
