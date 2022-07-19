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
import com.hothome.model.BiddingEntity;
import com.hothome.model.UserEntity;
import com.hothome.repository.BiddingRepository;
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
public class BiddingControllerTest {

	
	   MockMvc mockMvc;

		@Autowired
		WebApplicationContext context;
		
	    @Autowired
	    ObjectMapper objectMapper;

	    @Autowired
	    BiddingRepository bidRepo;
	    
	    ArrayList<BiddingEntity> bids  = null;
	    
	    @BeforeEach
	    public void setUp(WebApplicationContext webContext, RestDocumentationContextProvider restDocumentationContextProvider) {

	        this.mockMvc = MockMvcBuilders.webAppContextSetup(webContext)
	            .apply(documentationConfiguration(restDocumentationContextProvider))
	            .alwaysDo(document("{method-name}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
	            .build();
	        
	        this.bids = (ArrayList<BiddingEntity>) this.bidRepo.findAll();
	        System.err.println(this.bids);
	        
	    }
	    @Test
	    public void getBidById() throws Exception{
	        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/bid/findById/{id}",1 ))
	                .andExpect(status().isOk())
	                .andExpect(MockMvcResultMatchers.content().string(containsString("1")))
	                .andDo(document("bidding/get-bid-by-id",pathParameters(parameterWithName("id")
	                        .description("Bid Unique Identifier")),responseFields(
	                                fieldWithPath("id").description("Bid Unique Identifier"),
	                                fieldWithPath("createdAt").description("Bid Creation Date"),
	                                fieldWithPath("modifiedAt").description("Last updation of Bid")
	                		)));
	    }
}
