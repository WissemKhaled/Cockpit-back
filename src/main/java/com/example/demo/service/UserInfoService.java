package com.example.demo.service;

import java.time.LocalDate;
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
import com.example.demo.dto.CreateUserMapperEntityDTO;
import com.example.demo.dto.UUserDTO;
import com.example.demo.dto.UUserMapperEntityDTO;
import com.example.demo.entity.UUser;
import com.example.demo.mappers.UUserMapper;

@Service
public class UserInfoService implements UserDetailsService {
	@Autowired
	private PasswordEncoder encoder;

	private static final Logger LOG = LoggerFactory.getLogger(UserInfoService.class);

	@Autowired
	private UUserMapper userMapper;

	@Autowired
	private UUserMapperEntityDTO uUserMapperEntityDTO;
	
	@Autowired
	private CreateUserMapperEntityDTO createUserMapperEntityDTO;

	
	/*
	 * Méthode de sprig security qui sert à lier l'urtilisateur à ses infos
	 * */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<UUser> userDetail = userMapper.findByEmail(email);

		// Conversion de userDetail vers UserInfoDetails
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
	}
	
	/*
	 * Méthode qui retourne les informations de l'utilisateur en fonction de son email
	 * */
	public UUserDTO findUserByEmail(String email) {
		try {
			if (email != null && !email.isBlank()) {
				Optional<UUser> foundUser = userMapper.findByEmail(email);
				UUser userInfo = foundUser.orElseThrow(() -> new UsernameNotFoundException("User not found " + email));
				UUserDTO userInfoDTO = uUserMapperEntityDTO.toDto(userInfo);

				return userInfoDTO;
			} else {
				throw new IllegalArgumentException("Invalid username");
			}
		} catch (UsernameNotFoundException e) {
			LOG.error("User not found: {}", e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Méthode qui retourne les informations de l'utilisateur en fonction de son id
	 * */
	public UserDetails findById(int id) throws UsernameNotFoundException {
		Optional<UUser> userDetail = userMapper.findById(id);

		// Converting userDetail to UserInfoDetails
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + id));
	}

	public String addUser(CreateUserDTO userDTO) {
        UUser user = createUserMapperEntityDTO.toUser(userDTO);
        user.setUPassword(encoder.encode(user.getUPassword()));
        user.setUInsertionDate(LocalDate.now());
        
        try {
        	 userMapper.insert(user);
        	 LOG.info("User Added Successfully");
             return "User Added Successfully";
        } catch(Exception  ex) {
        	// Log the exception for debugging
            LOG.error("Error adding user: " + ex.getMessage(), ex);
            throw new RuntimeException("Error adding user. Please try again later");
        }
    }
}
