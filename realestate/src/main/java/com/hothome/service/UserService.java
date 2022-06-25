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

import java.util.ArrayList;

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
	
	public ArrayList<UserEntity> listAll(){
		ArrayList<UserEntity> temp = (ArrayList<UserEntity>) userRepository.findAll();
		return temp;
	}
	
	public UserEntity save(UserEntity user) {
		boolean isUpdatingUser = (user.getId() != null);
		if(false) {
			UserEntity temp = userRepository.findById(user.getId()).get();
			
		}
		else {
			user.setPassword(encoder.encodePassword(user.getPassword()));
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
	
	public String checkEmailUnique(String email) {
		UserEntity user = userRepository.getUserEntityByEmail(email);
		
		if(user == null) {
			return "OK";
		}
		else {
			return "Duplicated";
		}
		
	}
	
}
