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
import com.hothome.model.PropertyEntity;
import com.hothome.model.UserEntity;
import com.hothome.service.BiddingService;
import com.hothome.service.UserService;


public class PropertyValidationTest {
	private static ValidatorFactory validatorFactory;
	private static Validator validator;
	@BeforeEach
	public  void createValidator() {
	    validatorFactory = Validation.buildDefaultValidatorFactory();
	    
	    validator = validatorFactory.getValidator();
	}
	@Test
	public void propertyFieldPatternTest() {
		PropertyEntity property = new PropertyEntity();
		property.setName("@adsa");property.setBathroomDetails("a@");property.setCity("@A");property.setCustomerPrice("@1");property.setDescription("@reeA");
		property.setLivingArea("@3");property.setPostalCode("@W");property.setPropertyType("@A");property.setRoomDetails("@2");property.setStreet("@A");
	    Set<ConstraintViolation<PropertyEntity>> violations = validator.validate(property);
	    Iterator<ConstraintViolation<PropertyEntity>> namesIterator = violations.iterator();
	    while(namesIterator.hasNext()) {
	    		ConstraintViolation<PropertyEntity> violation = namesIterator.next();
	    		System.err.println(violation.getPropertyPath() + ": [" + violation.getMessage() + "]");
	    }
	    assertTrue(violations.size() > 0);
	}
	@Test
	public void propertyFieldSizeTest() {
		PropertyEntity property = new PropertyEntity();
		property.setName("1");property.setBathroomDetails("a");property.setCity("A");property.setCustomerPrice("1");property.setDescription("A");
		property.setLivingArea("3");property.setPostalCode("W");property.setPropertyType("A");property.setRoomDetails("2");property.setStreet("A");
	    Set<ConstraintViolation<PropertyEntity>> violations = validator.validate(property);
	    Iterator<ConstraintViolation<PropertyEntity>> namesIterator = violations.iterator();
	    while(namesIterator.hasNext()) {
	    		ConstraintViolation<PropertyEntity> violation = namesIterator.next();
	    		System.err.println(violation.getPropertyPath() + ": [" + violation.getMessage() + "]");
	    }
	    assertTrue(violations.size() > 0);
	}
	@Test
	public void propertyFieldNotNullTest() {
		PropertyEntity property = new PropertyEntity();
	    Set<ConstraintViolation<PropertyEntity>> violations = validator.validate(property);
	    Iterator<ConstraintViolation<PropertyEntity>> namesIterator = violations.iterator();
	    while(namesIterator.hasNext()) {
	    		ConstraintViolation<PropertyEntity> violation = namesIterator.next();
	    		System.err.println(violation.getPropertyPath() + ": [" + violation.getMessage() + "]");
	    }
	    assertFalse(violations.isEmpty());
	}
	
	@Test
	public void propertyValidTest() {
	    PropertyEntity property = new PropertyEntity();
	    property.setName("House");property.setBathroomDetails("12");property.setCity("Brampton");property.setCustomerPrice("266566");property.setDescription("NearSheridanCollege");
		property.setBuilderPrice("1214578");
	    property.setLivingArea("12145");property.setPostalCode("L6Y5A2");property.setPropertyType("Apratment");property.setRoomDetails("10");property.setStreet("Oaklea");
	    Set<ConstraintViolation<PropertyEntity>> violations = validator.validate(property);
	    Iterator<ConstraintViolation<PropertyEntity>> namesIterator = violations.iterator();
	    while(namesIterator.hasNext()) {
	    		ConstraintViolation<PropertyEntity> violation = namesIterator.next();
	    		System.err.println(violation.getPropertyPath() + ": [" + violation.getMessage() + "]");
	    }
	    assertTrue(violations.isEmpty());
	}
	@AfterClass
	public static void close() {
	    validatorFactory.close();
	}
}
