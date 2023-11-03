package com.example.demo.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.demo.entity.UUser;

public class UserInfoDetails implements UserDetails {

	private String name; 
	private String password; 
	private List<GrantedAuthority> authorities; 

	public UserInfoDetails(UUser userInfo) { 
		name = userInfo.getUEmail(); 
		password = userInfo.getUPassword();
	} 

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { 
		return authorities; 
	} 

	@Override
	public String getPassword() { 
		return password; 
	} 

	@Override
	public String getUsername() { 
		return name; 
	} 

	@Override
	public boolean isAccountNonExpired() { 
		return true; 
	} 

	@Override
	public boolean isAccountNonLocked() { 
		return true; 
	} 

	@Override
	public boolean isCredentialsNonExpired() { 
		return true; 
	} 

	@Override
	public boolean isEnabled() { 
		return true; 
	}
}
