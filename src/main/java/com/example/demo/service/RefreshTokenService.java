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

import lombok.extern.java.Log;

@Log
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
        
        log.info("refreshToken suivant créé : " + refreshToken);
        
        // Insère le refresh token en base de donnée et récupère la clé générée (rtId)
        int rowsAffected = refreshTokenMapper.insert(refreshToken);
        
        if (rowsAffected > 0) {
            int generatedRtId = refreshToken.getRtId();
            log.info("RefreshToken inséré en base de donnée avec le rtId: " + generatedRtId);
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
        if (!refreshToken.isPresent()) {
            log.warning("Le refresh token avec la clé '" + token + "' n'a pas été trouvé dans la base de données.");
        }
        return Optional.ofNullable(refreshToken.orElseThrow(() -> new GeneralException("Le refresh token avec la clé '" + token + "' n'a pas été trouvé dans la base de données.")));
    }


    
    public RefreshToken verifyExpiration(RefreshToken token) throws GeneralException {
    	try {
    		if (token.getRtExpiryDate().compareTo(Instant.now()) < 0) {
                refreshTokenMapper.delete(token.getRtToken());
                log.info(token.getRtToken() + " Refresh token expiré. Suppression de la base de donnée");
                throw new GeneralException("Refresh token expiré. Veuillez vous reconnecter");
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
        	log.info("Refresh token associé au User avec l'Id " + userId + " supprimé de la base de donnée");
        }
    }

}
