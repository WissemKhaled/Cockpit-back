package com.example.demo.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.UUserDTO;
import com.example.demo.dto.UUserMapperEntityDTO;
import com.example.demo.entity.UUser;
import com.example.demo.mappers.UUserMapper;

@Service
public class UserInfoService  implements UserDetailsService {
	@Autowired
	private PasswordEncoder encoder; 
	
	private static final Logger LOG = LoggerFactory.getLogger(UserInfoService.class);
	
	@Autowired
	private UUserMapper userMapper; // maybatis mapper (to make sql request)
	
	@Autowired
	private UUserMapperEntityDTO uUserMapperEntityDTO;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		LOG.info("loadByUsername function");

		LOG.info("email = " + email);
		
		Optional<UUser> userDetail = userMapper.findByEmail(email); 
		
		LOG.info("userDetail = " + userDetail);

		// Converting userDetail to UserInfoDetails 
		return userDetail.map(UserInfoDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("User not found " + email)); 
	}
	
	public UUserDTO findUserByEmail(String email) {
	    try {
	        if (email != null && !email.isBlank()) {
	            Optional<UUser> foundUser = userMapper.findByEmail(email);
	            
	            UUser userInfo = foundUser.orElseThrow(() -> new UsernameNotFoundException("User not found " + email));

	            // Use UserInfoMapper to convert UUser to UserInfoDTO
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
	
	public String addUser(UUser userInfo) {
	    userInfo.setUPassword(encoder.encode(userInfo.getUPassword()));
	    userInfo.setURoles("ROLE_USER");
	    userMapper.insert(userInfo);
	    return "User Added Successfully";
	}
}
