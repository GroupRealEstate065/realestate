package com.hothome.realestate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hothome.model.UserEntity;
import com.hothome.repository.UserRepository;
import com.hothome.service.UserService;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

	@MockBean private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	
	@Test
	public void checkUniqueEmailCase1() {
		String email = "karanpartapsingh20@gmail.com";
		UserEntity temp = new UserEntity();
		
		Mockito.when(userRepository.getUserEntityByEmail(email)).thenReturn(null);
		String reponse = userService.checkEmailUnique(email);
		
		assertEquals("OK", reponse);
	}
	@Test
	public void checkUniqueEmailCase2() {
		String email = "karanpartapsingh20@gmail.com";
		UserEntity temp = new UserEntity();
		
		Mockito.when(userRepository.getUserEntityByEmail(email)).thenReturn(temp);
		String reponse = userService.checkEmailUnique(email);
		
		assertEquals("Duplicated", reponse);
	}
}
