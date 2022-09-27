package com.example.vmo1.service.impl;

import com.example.vmo1.commons.exceptions.TokenRefreshException;
import com.example.vmo1.model.entity.RefreshToken;
import com.example.vmo1.repository.AccountRepository;
import com.example.vmo1.repository.RefreshTokenRepository;
import com.example.vmo1.service.RefeshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefeshTokenServiceImpl implements RefeshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long accountId) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setAccount(accountRepository.findById(accountId).get());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusSeconds(86400000));

        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpiryDate().compareTo(LocalDateTime.now()) < 0){
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new sign in request");
        }
        return token;
    }

    @Override
    public int deleteByUserId(Long accountId) {

        return refreshTokenRepository.deleteByAccount(accountRepository.findById(accountId).get());
    }
}
