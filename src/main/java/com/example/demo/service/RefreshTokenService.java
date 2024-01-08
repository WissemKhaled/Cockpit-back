package com.example.demo.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.builder.RefreshTokenBuilder;
import com.example.demo.entity.RefreshToken;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.RefreshTokenMapper;
import com.example.demo.mappers.UUserMapper;

@Service
public class RefreshTokenService {

	private static final Logger LOG = getLogger(RefreshTokenService.class);
	private RefreshTokenMapper refreshTokenMapper;
	private UUserMapper uUserMapper;
	
	@Value("${refresh.token.expiration.duration}")
	private int refreshTokenExpirationDuration;

	public RefreshTokenService(RefreshTokenMapper refreshTokenMapper, UUserMapper uUserMapper) {
		this.refreshTokenMapper = refreshTokenMapper;
		this.uUserMapper = uUserMapper;
	}
	
	public RefreshToken createRefreshToken(String email) {
		RefreshToken refreshToken = new RefreshTokenBuilder()
				.withUUser(uUserMapper.findUserByEmail(email).get())
				.withRtToken(UUID.randomUUID().toString())
				.withRtExpiryDate(Instant.now().plusSeconds(refreshTokenExpirationDuration))
				.build();
//		RefreshToken refreshToken = RefreshToken.builder()
//				.uUser(uUserMapper.findByEmail(email).get())
//				.rtToken(UUID.randomUUID().toString())
//				.rtExpiryDate(Instant.now().plusSeconds(refreshTokenExpirationDuration))
//				.build();

		LOG.info("refreshToken suivant créé : " + refreshToken);

		// Insère le refresh token en base de donnée et récupère la clé générée (rtId)
		int rowsAffected = refreshTokenMapper.insertRefreshToken(refreshToken);

		if (rowsAffected > 0) {
			int generatedRtId = refreshToken.getRtId();
			LOG.info("RefreshToken inséré en base de donnée avec le rtId: " + generatedRtId);
			return refreshToken;
		} else {
			return null;
		}
	}

	/*
	 * Méthode renvoyant le refreshtoken en fonction de la clé token passé en
	 * paramètre
	 */
	public Optional<RefreshToken> findByToken(String token) throws GeneralException {
		Optional<RefreshToken> refreshToken = refreshTokenMapper.findRefreshTokenByToken(token);
		if (!refreshToken.isPresent()) {
			LOG.warn("Le refresh token avec la clé '" + token + "' n'a pas été trouvé dans la base de données.");
		}
		return Optional.ofNullable(refreshToken.orElseThrow(() -> new GeneralException(
				"Le refresh token avec la clé '" + token + "' n'a pas été trouvé dans la base de données.")));
	}

	public RefreshToken verifyExpiration(RefreshToken token) throws GeneralException {
		try {
			if (token.getRtExpiryDate().compareTo(Instant.now()) < 0) {
				refreshTokenMapper.deleteRefreshTokenByToken(token.getRtToken());
				LOG.info(token.getRtToken() + " Refresh token expiré. Suppression de la base de donnée");
				throw new GeneralException("Refresh token expiré. Veuillez vous reconnecter");
			}
		} catch (GeneralException e) {
			throw e;
		}

		return token;
	}

	@Transactional
	public void deleteTokenByUserId(int userId) {
		Optional<RefreshToken> refreshToken = refreshTokenMapper.findRefreshTokenByUserId(userId);

		if (refreshToken.isPresent()) {
			refreshTokenMapper.deleteRefreshTokenById(userId);
			LOG.info("Refresh token associé au User avec l'Id " + userId + " supprimé de la base de donnée");
		}
	}

}
