package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private UUserMapper userMapper;

	@Autowired
	private UUserMapperEntityDTO uUserMapperEntityDTO;
	
	@Autowired
	private CreateUserMapperEntityDTO createUserMapperEntityDTO;
	
	private static final Logger log = LoggerFactory.getLogger(UserInfoService.class);

	/**
	 * Méthode de sprig security qui sert à lier l'urtilisateur à ses infos
	*/
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UUser> userDetail = userMapper.findByEmail(email);

		// Conversion de userDetail vers UserInfoDetails
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé " + email));
	}
	
	/**
	 * Méthode qui retourne les informations de l'utilisateur en fonction de son email
	*/
	public UUserDTO findUserByEmail(String email) {
		try {
			if (email != null && !email.isBlank()) {
				Optional<UUser> foundUser = userMapper.findByEmail(email);
				UUser userInfo = foundUser.orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé " + email));
				UUserDTO userInfoDTO = uUserMapperEntityDTO.toDto(userInfo);
				
				return userInfoDTO;
			} else {
				log.error(String.format("Email invalide %s" + email));
				throw new IllegalArgumentException("Email invalide");
			}
		} catch (UsernameNotFoundException e) {
			log.error(String.format("Utilisateur non trouvé : %o", e.getMessage(), e));
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
	 * @throws GeneralException 
	 */
	public String addUser(CreateUserDTO userDTO) throws GeneralException {
	    // Check if the user with the given email already exists
	    Optional<UUser> existingUser = userMapper.findByEmail(userDTO.getUEmail());
	    if (existingUser.isPresent()) {
	        log.warn("Utilisateur avec l'email '" + userDTO.getUEmail() + "' existe déjà");
	        throw new GeneralException("Un utilisateur avec cet email existe déjà");
	    }

	    UUser user = createUserMapperEntityDTO.toUser(userDTO);
	    user.setUPassword(encoder.encode(user.getUPassword()));
	    user.setUInsertionDate(LocalDateTime.now());
	    user.setUStatus(true);

	    try {
	        userMapper.insert(user);
	        log.info("Utilisateur '" + user.getUEmail() + "' ajouté avec succès");
	        return "Utilisateur '" + user.getUEmail() + "' ajouté avec succès";
	    } catch (Exception ex) {
	        log.error("Erreur d'ajout d'utilisateur: " + ex.getMessage());
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
	            log.error("Échec de mise à jour du mot de passe dans la base de données");
	            throw new GeneralException("Échec de mise à jour du mot de passe dans la base de données");
	        } else {
	            log.info("mot de passe mis à jour pour l'utilisateur : " + email);
	        }
	    } else {
	        log.warn("Utilisateur non trouvé avec l'email : " + email);
	        throw new GeneralException("Utilisateur non trouvé avec l'email : " + email);
	    }
	}
}
