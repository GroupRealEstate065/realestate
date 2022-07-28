package com.hothome.realestate.service;

import static com.hothome.constant.AuthenticationType.Database;
import static com.hothome.constant.Authority.ADMIN_AUTHORITIES;
import static com.hothome.constant.Roles.ROLE_ADMIN;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hothome.exception.user.UserNotFoundException;
import com.hothome.model.UserEntity;
import com.hothome.repository.UserRepository;
import com.hothome.service.UserService;
import com.supportportal.exception.domain.EmailExistException;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UserServiceTest {

	@MockBean private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService;
	
	
	@Test
	public void checkUniqueEmailCase1() throws EmailExistException {
		String email = "karanpartapsingh20@gmail.com";
		UserEntity temp = new UserEntity();
		
		Mockito.when(userRepository.getUserEntityByEmail(email)).thenReturn(null);
		String reponse = userService.checkEmailUnique(email);
		
		assertEquals("OK", reponse);
	}
	@Test
	public void checkUniqueEmailCase2() throws EmailExistException {
		String email = "karanpartapsingh20@gmail.com";
		UserEntity temp = new UserEntity();
		
		Mockito.when(userRepository.getUserEntityByEmail(email)).thenReturn(temp);
		String reponse = userService.checkEmailUnique(email);
		
		assertEquals("Duplicated", reponse);
	}
	
	@Test
	public void testListAllUser() {
		ArrayList<UserEntity> list = (ArrayList<UserEntity>) this.userRepository.findAll();
		
		Mockito.when(userRepository.findAll()).thenReturn(list);
		
		ArrayList<UserEntity> serviceList = this.userService.listAll();
		
		assertEquals(list, serviceList);
	}
	@Test
	public void testfindById() throws UserNotFoundException {
		UserEntity entity = new UserEntity();
		entity.setFirstName("Karanpartap");
		entity.setRole(ROLE_ADMIN);
		entity.setEmail("karanpartapsingh20@gmail.com");
		
		Optional<UserEntity> entity1 = Optional.of(entity);
		
		Mockito.when(this.userRepository.findById(3L)).thenReturn(entity1);
		
		UserEntity user = this.userService.findById(3L);
		
		assertEquals(user.getFirstName(), entity.getFirstName());
		assertEquals(user.getRole(), entity.getRole());
		assertEquals(user.getEmail(), entity.getEmail());	
	}
	
	
	
}
