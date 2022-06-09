package com.hothome.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hothome.Utility.PasswordEncoder;
import com.hothome.constant.Roles;
import com.hothome.model.AdminEntity;
import com.hothome.repository.AdminRepository;

import static com.hothome.constant.Authority.*;
@Service
@Transactional
public class AdminService{
	
	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public AdminEntity getAdminByEmail(String email) {
		return adminRepository.getAdminEntityByEmail(email);
	}
	
	public AdminEntity save(AdminEntity user) {
		boolean isUpdatingUser = (user.getId() != null);
		if(isUpdatingUser) {
			
		}
		else {
			user.setPassword(encoder.encodePassword(user.getPassword()));
			user.setActive(true);
			user.setNotLocked(true);
			user.setAuthorities(ADMIN_AUTHORITIES);
			user.setRole(Roles.ROLE_ADMIN);
		}
		return adminRepository.save(user);	
	}
	
	
	
	
	
}
