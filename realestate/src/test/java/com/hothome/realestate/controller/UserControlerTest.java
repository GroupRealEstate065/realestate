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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hothome.SpringRestDocsConfig;
import com.hothome.dto.LoginDto;
import com.hothome.model.BiddingEntity;
import com.hothome.service.BiddingService;
import com.hothome.service.PropertyService;
import com.hothome.service.UserService;

@SpringBootTest(classes = SpringRestDocsConfig.class)
public class UserControlerTest {

	MockMvc mockMvc;
	@Autowired
	WebApplicationContext context;
	@Autowired
	private UserService userService;
	@Autowired
	ObjectMapper mapper;
	
  @BeforeEach
    public void setUp(WebApplicationContext webContext) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext).build();
    }
	@Test
	public void listAllUserTest() throws Exception {
		mockMvc.perform(get("/user/listAll"))
			.andDo(print())
			.andExpect(status().isOk());
	}
	@Test
	public void findByIdTest() throws Exception {
		mockMvc.perform(get("/user/findById/2")
			      .contentType(MediaType.APPLICATION_JSON))
					.andDo(print())
					.andExpect(status().isOk());
	}
	@Test
	public void loginByIdTest() throws Exception {
		LoginDto dto = new LoginDto("karanpartapsingh20@gmail.com", "H@m123456");
		mockMvc.perform(post("/login")
			      .contentType(MediaType.APPLICATION_JSON)
			      .content(mapper.writeValueAsString(dto)))
					.andDo(print())
					.andExpect(status().isOk());
	}
	@Test
	public void checkEmailTest() throws Exception {
		mockMvc.perform(get("/user/checkEmail")
			      .param("email","karanpartapsingh@gmail.com"))
					.andDo(print())
					.andExpect(status().isOk());
	}
	@Test
	public void saveUser() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		
		params.add("firstName","abadaac");params.add("lastName","xyz");params.add("email","karanpartap@gmail.com");params.add("phoneNumber","4587692348");
		params.add("password", "H@m123244354");params.add("postalCode","L6y5A2");params.add("role","ROLE_ADMIN");
		params.add("street", "Oaklea");params.add("city", "Brampton");
		
		mockMvc.perform(post("/register")
				.params(params))		
			.andExpect(status().isCreated());
	}
	
}
