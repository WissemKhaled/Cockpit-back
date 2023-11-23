package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.CreateUserDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.dto.UUserMapperEntityDTO;
import com.example.demo.dto.mapper.CreateUserMapperEntityDTO;
import com.example.demo.entity.UUser;
import com.example.demo.mappers.UUserMapper;

import lombok.extern.java.Log;

@Log
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
				log.severe(String.format("Email invalide %s" + email));
				throw new IllegalArgumentException("Email invalide");
			}
		} catch (UsernameNotFoundException e) {
			log.severe(String.format("Utilisateur non trouvé : {}", e.getMessage(), e));
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
	 * Méthode de crétion d'un utilisateur
	*/
	public String addUser(CreateUserDTO userDTO) {
        UUser user = createUserMapperEntityDTO.toUser(userDTO);
        user.setUPassword(encoder.encode(user.getUPassword()));
        user.setUInsertionDate(LocalDateTime.now());
        user.setUStatus(true);
        
        try {
        	 userMapper.insert(user);
        	 log.info("Utilisateur '" + user.getUEmail() + "' ajouté avec succès");
             return "Utilisateur '" + user.getUEmail() + "' ajouté avec succès";
        } catch(Exception  ex) {
        	log.severe("Error d'ajout d'utilisateur: " + ex.getMessage());
            throw new RuntimeException("Erreur d'ajout d'utilisateur. Veuillez réessayer plus tard");
        }
    }
}
