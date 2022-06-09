package com.hothome.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.hothome.model.AbstractEntity;

@Component
public class PasswordEncoder {

	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public String encodePassword(String password) {
		String encodedPassword = encoder.encode(password);
		return encodedPassword;
	}
	
}
