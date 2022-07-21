package com.hothome.realestate.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.hothome.constant.AuthenticationType;
import com.hothome.constant.Roles;
import com.hothome.model.UserEntity;
import com.hothome.repository.UserRepository;
import com.hothome.service.UserService;

import static com.hothome.constant.Authority.*;
import static com.hothome.constant.Roles.*;
import static com.hothome.constant.AuthenticationType.*;


@DataJpaTest(showSql = true)
@Rollback(value = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)

public class UserRepoTest {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	//@Test
	public void testCreateUser() {
		UserEntity entity = new UserEntity();
		
		entity.setFirstName("Karanpartap");
		entity.setLastName("Singh");
		entity.setAuthorities(ADMIN_AUTHORITIES);
		entity.setRole(ROLE_ADMIN);
		String pssword = encoder.encode("Aq@123456789");
		entity.setPassword(pssword);
		entity.setActive(true);
		entity.setNotLocked(true);
		entity.setEmail("karanpartapsingh20@gmail.com");
		entity.setPhoneNumber("14168457419");
		entity.setAuthenticationType(Database.toString());
		entity.setRole(ROLE_ADMIN);
		entity.setStreet("Oaklea Blvd");
		entity.setPostalCode("L6Y5A2");
		entity.setCity("Brampton");
		entity.setLicenseDoc("1233APO1");
		entity.setLicenseNumber("1233APO1");
		assertThat(userRepo.save(entity).getId() > 0);
	}

	//@Test
	public void testCreateUser2() {
		UserEntity entity = new UserEntity();
		
		entity.setFirstName("Gagandeep");
		entity.setLastName("Singh");
		entity.setAuthorities(ADMIN_AUTHORITIES);
		entity.setRole(ROLE_ADMIN);
		entity.setPassword("Aq@123456789");
		entity.setActive(true);
		entity.setNotLocked(true);
		entity.setEmail("gagandeepsingh20@gmail.com");
		entity.setPhoneNumber("14168457419");
		entity.setAuthenticationType(Database.toString());
		entity.setRole(ROLE_ADMIN);
		entity.setStreet("Oaklea Blvd");
		entity.setPostalCode("L6Y5A2");
		entity.setCity("Brampton");
		entity.setLicenseDoc("12336APII");
		entity.setLicenseNumber("12336APII");
		assertThat(userRepo.save(entity).getId() > 0);
	}

	@Test
	public void testListAllUser() {
		Iterable<UserEntity> temp = userRepo.findAll();
		temp.forEach(user ->{System.err.println(user);});
	}
	
	
}
