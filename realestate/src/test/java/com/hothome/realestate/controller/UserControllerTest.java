package com.hothome.realestate.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hothome.RealestateApplication;
import com.hothome.SpringRestDocsConfig;
import com.hothome.model.UserEntity;
import com.hothome.repository.UserRepository;
import com.hothome.service.UserService;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

import com.hothome.controller.*;

@ExtendWith({RestDocumentationExtension.class,SpringExtension.class})
@SpringBootTest(classes = RealestateApplication.class)
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class UserControllerTest {

	//@Autowired
    MockMvc mockMvc;

	@Autowired
	WebApplicationContext context;
	
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserRepository userRepo;
    
    ArrayList<UserEntity> users  = null;
    
    @BeforeEach
    public void setUp(WebApplicationContext webContext, RestDocumentationContextProvider restDocumentationContextProvider) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext)
            .apply(documentationConfiguration(restDocumentationContextProvider))
            .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
            .build();
        
        this.users = (ArrayList<UserEntity>) this.userRepo.findAll();
        System.err.println(this.users.size());
    }

	/*
	 * //@Test public void testGetAllUser() throws Exception{
	 * this.mockMvc.perform(get("/user/listAll") .contentType("application/json"))
	 * .andExpect(status().isOk())
	 * .andExpect(MockMvcResultMatchers.content().string(containsString(
	 * "karanpartapsingh20@gmail.com"))) .andDo(document("user/list-all-user",
	 * responseFields( fieldWithPath("id").description("User Unique Identifier"),
	 * fieldWithPath("createdAt").description("User Creation Date"),
	 * fieldWithPath("modifiedAt").description("Last updation of User"),
	 * fieldWithPath("firstName").description("First Name of the user"),
	 * fieldWithPath("lastName").description("Last Name of the user"),
	 * fieldWithPath("authorities").description("User Authorities"),
	 * fieldWithPath("role").description("User Role"),
	 * fieldWithPath("email").description("User Registered Email"),
	 * fieldWithPath("authenticationType").description("User Login mode"),
	 * fieldWithPath("profileImage").description("User Image URL"),
	 * fieldWithPath("phoneNumber").description("User Phone Number"),
	 * fieldWithPath("street").description("User Address Street"),
	 * fieldWithPath("city").description("User Address City"),
	 * fieldWithPath("postalCode").description("User Address Postal Code"),
	 * fieldWithPath("licenseNumber").description("Builder License Number"),
	 * fieldWithPath("licenseDoc").description("Builder License Document URL"),
	 * fieldWithPath("active").description("User Active"),
	 * fieldWithPath("notLocked").description("User Account Status"),
	 * fieldWithPath("profileImageUrl").description("User Image URL")) )); }
	 */
    @Test
    public void testGetAllUser() throws Exception{
    	this.mockMvc.perform(get("/user/listAll")
    			.contentType("application/json"))
    	.andExpect(status().isOk())
    	.andExpect(MockMvcResultMatchers.content().string(containsString("karanpartapsingh20@gmail.com")))
    	.andDo(print());
    }
    @Test
    public void getUserById() throws Exception{
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/user/findById/{id}",1 ))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("karanpartapsingh20@gmail.com")))
                .andDo(document("user/get-user-by-id",pathParameters(parameterWithName("id")
                        .description("User Unique Identifier")),responseFields(
                                fieldWithPath("id").description("User Unique Identifier"),
                                fieldWithPath("createdAt").description("User Creation Date"),
                                fieldWithPath("modifiedAt").description("Last updation of User"),
                                fieldWithPath("firstName").description("First Name of the user"),
                                fieldWithPath("lastName").description("Last Name of the user"),
                                fieldWithPath("authorities").description("User Authorities"),
                                fieldWithPath("role").description("User Role"),
                                fieldWithPath("email").description("User Registered Email"),
                                fieldWithPath("authenticationType").description("User Login mode"),
                                fieldWithPath("profileImage").description("User Image URL"),
                                fieldWithPath("phoneNumber").description("User Phone Number"),
                                fieldWithPath("street").description("User Address Street"),
                                fieldWithPath("city").description("User Address City"),
                                fieldWithPath("postalCode").description("User Address Postal Code"),
                                fieldWithPath("licenseNumber").description("Builder License Number"),
                                fieldWithPath("licenseDoc").description("Builder License Document URL"),
                                fieldWithPath("active").description("User Active"),
                                fieldWithPath("notLocked").description("User Account Status"),
                                fieldWithPath("profileImageUrl").description("User Image URL"))));
    }
    @Test
    public void checkEmail() throws Exception{
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/user/checkEmail?email=karanpartapsingh20@gmail.com"))
                .andExpect(status().isOk())
                //.andExpect(MockMvcResultMatchers.content().string(containsString("karanpartapsingh20@gmail.com")))
                .andDo(document("user/check-user-email-unique"))
                .andDo(print());
	}

}
