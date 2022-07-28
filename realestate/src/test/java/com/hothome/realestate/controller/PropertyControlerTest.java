package com.hothome.realestate.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import com.hothome.SpringRestDocsConfig;
import com.hothome.model.BiddingEntity;
import com.hothome.service.BiddingService;
import com.hothome.service.PropertyService;
import com.hothome.service.UserService;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRestDocsConfig.class)
public class PropertyControlerTest {

	MockMvc mockMvc;
	
	@Autowired
	WebApplicationContext context;
	
	
	@Autowired
	private BiddingService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PropertyService propertyService;
	
	  @BeforeEach
	    public void setUp(WebApplicationContext webContext) {
	        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
	    }
	@Test
	public void listAllProperty() throws Exception {
		mockMvc.perform(get("/property/findAll"))
			.andDo(print())
			.andExpect(status().isOk());
	}
	@Test
	public void findByIdTest() throws Exception {
		mockMvc.perform(get("/property/findById/2")
			      .contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk());
	}
	@Test
	public void saveProperty() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("name","House");params.add("bathroomDetails","10");params.add("city","Brampton");params.add("customerPrice","4587698");
		params.add("description", "Thehalsiadjis");params.add("livingArea","2334");params.add("postalCode","L6y5A2");params.add("propertyType","Apartment");
		params.add("roomDetails","10");params.add("street", "Oaklea");params.add("parking","true");params.add("builderPrice","1324566");
		mockMvc.perform(post("/property/save")
				.params(params))
			
			.andExpect(status().isCreated());
	}
	
}
