package com.hothome.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hothome.Utility.PasswordEncoder;
import com.hothome.constant.AuthenticationType;
import com.hothome.constant.Roles;
import com.hothome.model.UserEntity;
import com.hothome.repository.UserRepository;

import static com.hothome.constant.Authority.*;

import java.util.ArrayList;
import java.util.Optional;

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
	public UserEntity findById(Long id) {
		Optional<UserEntity>  user = this.userRepository.findById(id);
		if(user == null) {
			throw new UsernameNotFoundException("User does not Exist");
		}
		return user.get();
	}
	
	public UserEntity save(UserEntity user) {
		boolean isUpdatingUser = (user.getId() != null);
		if(isUpdatingUser) {
			UserEntity temp = userRepository.findById(user.getId()).get();
			user.setPassword(encoder.encodePassword(user.getPassword()));
			user.setCreatedAt(temp.getCreatedAt());
			user.setRole(temp.getRole());
			if(user.profileImage == "") {
				user.setProfileImage(temp.profileImage);
			}
			if(user.getLicenseDoc() == "") {
				user.setLicenseDoc(temp.getLicenseDoc());
			}
		}
		else {
			user.setPassword(encoder.encodePassword(user.getPassword()));
		}
		return userRepository.save(user);	
	}
	
	public boolean deleteById(Long id) {
		Optional<UserEntity> user = this.userRepository.findById(id);
		if(user != null) {
			this.userRepository.delete(user.get());
			return true;
		}
		else {
			throw new UsernameNotFoundException("User does not Exist");
		}
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
