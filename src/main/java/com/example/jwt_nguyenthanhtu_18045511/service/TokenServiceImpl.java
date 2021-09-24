package com.example.jwt_nguyenthanhtu_18045511.service;

import com.example.jwt_nguyenthanhtu_18045511.entity.Token;
import com.example.jwt_nguyenthanhtu_18045511.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Override
    public Token createToken(Token token) {
        return tokenRepository.saveAndFlush(token);
    }

    @Override
    public Token findByToken(String token) {
        return tokenRepository.findByToken(token);
    }
}
