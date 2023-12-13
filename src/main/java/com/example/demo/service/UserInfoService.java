package com.example.demo.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.dto.mapper.CreateUserMapperEntityDTO;
import com.example.demo.dto.mapper.UUserMapperEntityDTO;
import com.example.demo.entity.UUser;
import com.example.demo.exception.GeneralException;
import com.example.demo.mappers.UUserMapper;

@Service
public class UserInfoService implements UserDetailsService {

	private static final Logger LOG = getLogger(UserInfoService.class);

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UUserMapper userMapper;

	@Autowired
	private UUserMapperEntityDTO uUserMapperEntityDTO;

	@Autowired
	private CreateUserMapperEntityDTO createUserMapperEntityDTO;

	/**
	 * Méthode de spring security qui sert à lier l'urtilisateur à ses infos
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UUser> userDetail = userMapper.findByEmail(email);

		// Conversion de userDetail vers UserInfoDetails
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé " + email));
	}

	/**
	 * Méthode qui retourne les informations de l'utilisateur en fonction de son
	 * email
	 */
	public UUserDTO findUserByEmail(String email) {
		try {
			if (email != null && !email.isBlank()) {
				Optional<UUser> foundUser = userMapper.findByEmail(email);
				UUser userInfo = foundUser
						.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé " + email));
				UUserDTO userInfoDTO = uUserMapperEntityDTO.toDto(userInfo);

				return userInfoDTO;
			} else {
				LOG.error(String.format("Email invalide %s" + email));
				throw new IllegalArgumentException("Email invalide");
			}
		} catch (UsernameNotFoundException e) {
			LOG.error(String.format("Utilisateur non trouvé : %o", e.getMessage(), e));
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Méthode qui retourne les informations de l'utilisateur en fonction de son id
	 */
	public UserDetails findById(int id) throws UsernameNotFoundException {
		Optional<UUser> userDetail = userMapper.findById(id);

		// Converting userDetail to UserInfoDetails
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'id : " + id));
	}

	/**
	 * Méthode de création d'un utilisateur
	 * 
	 * @throws GeneralException
	 */
	public String addUser(CreateUserDTO userDTO) throws GeneralException {
		// Check if the user with the given email already exists
		Optional<UUser> existingUser = userMapper.findByEmail(userDTO.getUEmail());
		if (existingUser.isPresent()) {
			LOG.warn("Utilisateur avec l'email '" + userDTO.getUEmail() + "' existe déjà");
			throw new GeneralException("Un utilisateur avec cet email existe déjà");
		}

		UUser user = createUserMapperEntityDTO.toUser(userDTO);
		user.setUPassword(encoder.encode(user.getUPassword()));
		user.setUInsertionDate(LocalDateTime.now());
		user.setUStatus(true);

		try {
			userMapper.insert(user);
			LOG.info("Utilisateur '" + user.getUEmail() + "' ajouté avec succès");
			return "Utilisateur '" + user.getUEmail() + "' ajouté avec succès";
		} catch (Exception ex) {
			LOG.error("Erreur d'ajout d'utilisateur: " + ex.getMessage());
			throw new GeneralException("Erreur d'ajout d'utilisateur. Veuillez réessayer plus tard");
		}
	}

	public void resetPassword(String newPassword, String email) throws GeneralException {
		Optional<UUser> userOptional = userMapper.findByEmail(email);
		if (userOptional.isPresent()) {
			UUser user = userOptional.get();
			user.setUPassword(newPassword);
			user.setULastUpdate(LocalDateTime.now());

			int isPasswordUpdated = userMapper.updatePassword(user);

			if (isPasswordUpdated == 0) {
				LOG.error("Échec de mise à jour du mot de passe dans la base de données");
				throw new GeneralException("Échec de mise à jour du mot de passe dans la base de données");
			} else {
				LOG.info("mot de passe mis à jour pour l'utilisateur : " + email);
			}
		} else {
			LOG.warn("Utilisateur non trouvé avec l'email : " + email);
			throw new GeneralException("Utilisateur non trouvé avec l'email : " + email);
		}
	}

}
