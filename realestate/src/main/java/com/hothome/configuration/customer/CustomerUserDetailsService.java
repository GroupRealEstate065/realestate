package com.hothome.configuration.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.hothome.model.UserEntity;
import com.hothome.repository.UserRepository;

public class CustomerUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = this.userRepository.getUserEntityByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("User Does Not Exist !!");
		}
		return new CustomerUserDetails(user);
	}

}
