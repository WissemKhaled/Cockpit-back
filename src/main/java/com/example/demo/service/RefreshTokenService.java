package com.example.demo.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.RefreshToken;
import com.example.demo.exception.GeneralException;
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

    public RefreshToken createRefreshToken(String email) {
        RefreshToken refreshToken = RefreshToken.builder()
            .uUser(uUserMapper.findByEmail(email).get())
            .rtToken(UUID.randomUUID().toString())
            .rtExpiryDate(Instant.now().plusSeconds(refreshTokenExpirationDuration))
            .build();
        
        // Insère le refresh token en base de donnée et récupère la clé générée (rtId)
        int rowsAffected = refreshTokenMapper.insert(refreshToken);
        
        if (rowsAffected > 0) {
            int generatedRtId = refreshToken.getRtId();
            return refreshToken;
        } else {
            return null;
        }
    }
    
    /*
     * Méthode renvoyant le refreshtoken en fonction de la clé token passé en paramètre
     * */
    public Optional<RefreshToken> findByToken(String token) throws GeneralException {
        Optional<RefreshToken> refreshToken = refreshTokenMapper.findByToken(token);
        return Optional.ofNullable(refreshToken.orElseThrow(() -> new GeneralException("Le refresh token avec la clé '" + token + "' n'a pas été trouvé dans la base de données.")));
    }


    
    public RefreshToken verifyExpiration(RefreshToken token) throws GeneralException {
    	try {
    		if (token.getRtExpiryDate().compareTo(Instant.now()) < 0) {
                refreshTokenMapper.delete(token.getRtToken());
                throw new GeneralException("Refresh token expired");
            }
    	} catch (GeneralException e) {
    		throw e;
    	}
        
        return token;
    }
    
    @Transactional
    public void deleteTokenByUserId(int userId) {
    	Optional<RefreshToken> refreshToken = refreshTokenMapper.findByUserId(userId);

        if (refreshToken.isPresent()) {
        	refreshTokenMapper.deleteRtById(userId);
        }
    }

}
