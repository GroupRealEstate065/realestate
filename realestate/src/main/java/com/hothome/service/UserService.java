package com.hothome.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hothome.Utility.PasswordEncoder;
import com.hothome.constant.AuthenticationType;
import com.hothome.constant.Roles;
import com.hothome.model.UserEntity;
import com.hothome.repository.UserRepository;

import static com.hothome.constant.Authority.*;

@Service
@Transactional
public class UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public UserEntity getUserByEmail(String email) {
		return userRepository.getUserEntityByEmail(email);
	}
	
	public UserEntity save(UserEntity user) {
		boolean isUpdatingUser = (user.getId() != null);
		if(false) {
			
		}
		else {
			user.setPassword(encoder.encodePassword(user.getPassword()));
			user.setActive(true);
			user.setNotLocked(true);
			user.setAuthorities(ADMIN_AUTHORITIES);
			user.setRole(Roles.ROLE_ADMIN);
		}
		return userRepository.save(user);	
	}
	
	public void updateAuthenticationType(Long userId,String authenticationType) {
		 this.userRepository.updateAuthenticationType(userId,authenticationType);
	}
	
	public UserEntity addNewCustomerUponOAuthLogin(String name, String email, String countryCode,
			AuthenticationType authenticationType) {
		UserEntity customer = new UserEntity();
		customer.setEmail(email);
		customer.setFirstName(name);		
		customer.setActive(true);
		customer.setRole(Roles.ROLE_CUSTOMER);
		customer.setAuthorities(USER_AUTHORITIES);
		customer.setAuthenticationType(authenticationType.toString());
		customer.setPassword("");
		return userRepository.save(customer);
	}	
	
}
