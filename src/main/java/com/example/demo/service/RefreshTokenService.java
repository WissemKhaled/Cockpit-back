package com.example.demo.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.demo.entity.RefreshToken;
import com.example.demo.mappers.RefreshTokenMapper;
import com.example.demo.mappers.UUserMapper;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenMapper refreshTokenMapper;
    
    @Autowired
    private UUserMapper uUserMapper;
    
    @Value("${refresh.token.expiration.duration}")
    private int refreshTokenExpirationDuration;
    
    private static final Logger LOG = LoggerFactory.getLogger(RefreshTokenService.class);

    public RefreshToken createRefreshToken(String email) {
        LOG.info("email: " + email);
        
        RefreshToken refreshToken = RefreshToken.builder()
            .uUser(uUserMapper.findByEmail(email).get())
            .rtToken(UUID.randomUUID().toString())
            .rtExpiryDate(Instant.now().plusSeconds(refreshTokenExpirationDuration))
            .build();
        
        // Insert the RefreshToken and retrieve the generated key (rtId)
        int rowsAffected = refreshTokenMapper.insert(refreshToken);
        
        if (rowsAffected > 0) {
            // The insert was successful, we can access the generated key from the RefreshToken
            int generatedRtId = refreshToken.getRtId();
            LOG.info("RefreshToken inserted with rtId: " + generatedRtId);
            
            // You can return the RefreshToken with the generated rtId
            return refreshToken;
        } else {
            // Handle the case where the insert failed
            LOG.error("RefreshToken insert failed.");
            // Return null or throw an exception to indicate the failure
            return null;
        }
    }

    public Optional<RefreshToken> findByToken(String token) {
    	LOG.error("findByToken: " + token);
        return refreshTokenMapper.findByToken(token);
    }
    
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getRtExpiryDate().compareTo(Instant.now()) < 0) {
        	refreshTokenMapper.delete(token);
            throw new RuntimeException(token.getRtToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}
