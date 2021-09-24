package com.example.jwt_nguyenthanhtu_18045511.service;

import com.example.jwt_nguyenthanhtu_18045511.authen.UserPrincipal;
import com.example.jwt_nguyenthanhtu_18045511.entity.User;
import com.example.jwt_nguyenthanhtu_18045511.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User createUser(User user) {
        return repository.saveAndFlush(user);
    }

    @Override
    public UserPrincipal findByUsername(String username) {
        User user = repository.findByUsername(username);
        UserPrincipal userPrincipal = new UserPrincipal();

        if(user != null) {
            Set<String> authorities = new HashSet<>();

            if (user.getRoles() != null)
                user.getRoles().forEach(r -> {
                    authorities.add(r.getRoleKey());
                    r.getPermissions().forEach(p -> authorities.add(p.getPermissionKey()));
                });

                userPrincipal.setUserId(user.getId());
                userPrincipal.setUsername(user.getUsername());
                userPrincipal.setPassword(user.getPassword());
                userPrincipal.setAuthorities(authorities);


        }
        return userPrincipal;
    }
}
