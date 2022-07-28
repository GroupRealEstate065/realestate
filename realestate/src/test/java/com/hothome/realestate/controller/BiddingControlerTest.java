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
import org.springframework.web.context.WebApplicationContext;

import com.hothome.SpringRestDocsConfig;
import com.hothome.model.BiddingEntity;
import com.hothome.service.BiddingService;
import com.hothome.service.PropertyService;
import com.hothome.service.UserService;

//@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringRestDocsConfig.class)
public class BiddingControlerTest {

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
	public void saveBid() throws Exception {
		mockMvc.perform(post("/bid/save")
				.param("price", "52364.32")
				.param("propertyId", "3")
				.param("userId", "3"))
			.andDo(print())
			.andExpect(status().isCreated());
	}
	@Test
	public void approveFinalBid() throws Exception {
		mockMvc.perform(post("/bid/finalBid")
				.param("bidId", "3")
				.param("propertyId", "3")
				.param("userId", "3")
				.param("type", "Customer"))
			.andDo(print())
			.andExpect(status().isCreated());
	}
	   
	@Test
	public void findByIdTest() throws Exception {
		mockMvc.perform(get("/bid/findById/2")
			      .contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk());
	}
	
	
}
