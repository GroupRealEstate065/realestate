package com.hothome.realestate.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hothome.SpringRestDocsConfig;
import com.hothome.model.BiddingEntity;
import com.hothome.model.UserEntity;
import com.hothome.service.BiddingService;
import com.hothome.service.UserService;


public class UserValidationTest {
	private static ValidatorFactory validatorFactory;
	private static Validator validator;
	@BeforeEach
	public  void createValidator() {
	    validatorFactory = Validation.buildDefaultValidatorFactory();
	    
	    validator = validatorFactory.getValidator();
	}
	@Test
	public void userFieldPatternTest() {
	    UserEntity user = new UserEntity();user.setEmail("ahadas.com");
	    user.setFirstName("1");user.setLastName("2");user.setPassword("abcvgd56");user.setPhoneNumber("@#!452");user.setStreet("!@#A");user.setPostalCode("!@#A");user.setCity("!@#s");
	    Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
	    Iterator<ConstraintViolation<UserEntity>> namesIterator = violations.iterator();
	    while(namesIterator.hasNext()) {
	    		ConstraintViolation<UserEntity> violation = namesIterator.next();
	    	   System.err.println(violation.getPropertyPath() + ": [" + violation.getMessage() + "]");
	    }
	    assertTrue(violations.size() > 0);
	}
	@Test
	public void userFieldSizeTest() {
	    UserEntity user = new UserEntity();
	    user.setFirstName("A");user.setLastName("A");user.setPassword("A");user.setPhoneNumber("452");user.setStreet("A");user.setPostalCode("A");user.setCity("s");
	    Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
	    Iterator<ConstraintViolation<UserEntity>> namesIterator = violations.iterator();
	    while(namesIterator.hasNext()) {
	    		ConstraintViolation<UserEntity> violation = namesIterator.next();
	    	   System.err.println(violation.getPropertyPath() + ": [" + violation.getMessage() + "]");
	    }
	    assertTrue(violations.size() > 0);
	}
	@Test
	public void userFieldNotNullTest() {
	    UserEntity user = new UserEntity();
	    Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
	    Iterator<ConstraintViolation<UserEntity>> namesIterator = violations.iterator();
	    while(namesIterator.hasNext()) {
	    		ConstraintViolation<UserEntity> violation = namesIterator.next();
	    	   System.err.println(violation.getPropertyPath() + ": [" + violation.getMessage() + "]");
	    }
	    assertFalse(violations.isEmpty());
	}
	
	@Test
	public void userValidTest() {
	    UserEntity user = new UserEntity();
	    user.setFirstName("Karanpartap");user.setLastName("Singh");user.setPassword("H@M123213");user.setPhoneNumber("4521232454654");
	    user.setStreet("Oaklea");user.setPostalCode("L6Y5A2");user.setCity("Brampton");user.setEmail("karanpartapsingh@gmail.com");
	    Set<ConstraintViolation<UserEntity>> violations = validator.validate(user);
	    Iterator<ConstraintViolation<UserEntity>> namesIterator = violations.iterator();
	    while(namesIterator.hasNext()) {
	    		ConstraintViolation<UserEntity> violation = namesIterator.next();
	    		System.err.println(violation.getPropertyPath() + ": [" + violation.getMessage() + "]");
	    }
	    assertTrue(violations.isEmpty());
	}
	@AfterClass
	public static void close() {
	    validatorFactory.close();
	}
}
