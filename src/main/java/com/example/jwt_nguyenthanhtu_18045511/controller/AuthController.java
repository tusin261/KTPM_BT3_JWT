package com.example.jwt_nguyenthanhtu_18045511.controller;

import com.example.jwt_nguyenthanhtu_18045511.authen.UserPrincipal;
import com.example.jwt_nguyenthanhtu_18045511.entity.Token;
import com.example.jwt_nguyenthanhtu_18045511.entity.User;
import com.example.jwt_nguyenthanhtu_18045511.service.TokenService;
import com.example.jwt_nguyenthanhtu_18045511.service.UserService;
import com.example.jwt_nguyenthanhtu_18045511.until.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/register")
    public User register(@RequestBody User user){
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userService.createUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        UserPrincipal userPrincipal =
                userService.findByUsername(user.getUsername());
        if(user ==null || !new BCryptPasswordEncoder()
                .matches(user.getPassword(),userPrincipal.getPassword())) {
            return "Account or password not valid";
        }
        Token token = new Token();
        token.setToken(jwtUtil.generateToken(userPrincipal));
        token.setTokenExpDate(jwtUtil.generateExpirationDate());
        tokenService.createToken(token);
        //System.out.println(token.getToken());
        return token.getToken();
    }
}
