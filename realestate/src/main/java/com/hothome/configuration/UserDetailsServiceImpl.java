package com.hothome.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hothome.model.UserEntity;
import com.hothome.model.UserLogged;
import com.hothome.model.UserPrincipal;

import com.hothome.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//if user is admin
		UserEntity admin = userRepository.getUserEntityByEmail(username);
		if(admin != null) {
			UserLogged temp = new UserLogged(admin.getId(), admin.getEmail(), admin.getName(), admin.getPassword(), admin.getRole().toString(), admin.getAuthorities(), admin.isActive(), admin.isNotLocked());
			UserPrincipal user = new UserPrincipal(temp);
			return user;
		}
		throw new UsernameNotFoundException("Could not find User");
		
	}

	
	
}
