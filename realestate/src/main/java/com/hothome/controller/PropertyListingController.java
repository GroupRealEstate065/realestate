package com.hothome.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hothome.Utility.AmazonS3Util;
import com.hothome.Utility.AwsS3Constant;
import com.hothome.jwt.JwtTokenProvider;
import com.hothome.model.BiddingEntity;
import com.hothome.model.PropertyEntity;
import com.hothome.service.PropertyService;

@RestController
@RequestMapping(value = "/property")
public class PropertyListingController {

	@Autowired
	private PropertyService propertyService; 
	
	@Autowired
	private JwtTokenProvider pro;
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public ResponseEntity<String> saveProperty(@RequestParam String name, @RequestParam String roomDetails,@RequestParam String description,@RequestParam boolean parking,
			@RequestParam String livingArea, @RequestParam String bathroomDetails, @RequestParam String builderPrice, @RequestParam String customerPrice,
			@RequestParam String propertyType, @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
			@RequestParam(required = false) MultipartFile[] files) 
	{
		String[] filesNameArray;
		PropertyEntity entity;
		if(files != null && files.length > 0) {
			filesNameArray = new String[files.length];
			for(int i = 0; i <files.length; i++) {
				filesNameArray[i] = files[i].getOriginalFilename();
			}
			entity = new PropertyEntity(name, roomDetails, description, parking, livingArea, bathroomDetails, builderPrice, customerPrice, propertyType, street, city, postalCode, filesNameArray);
		}
		else {
			entity = new PropertyEntity(name, roomDetails, description, parking, livingArea, bathroomDetails, builderPrice, customerPrice, propertyType, street, city, postalCode, null);
		}
		
		PropertyEntity savedProperty = this.propertyService.save(entity);
		if(files != null && files.length > 0) {
			for(int i = 0; i < files.length; i++) {
				String imageName = files[i].getOriginalFilename(); 
				  
				 String uploadDir = AwsS3Constant.PROPERTY_PHOTOS +"/" + entity.getId(); 
				 try { 
					  AmazonS3Util.uploadFile(uploadDir,imageName, files[i].getInputStream());
				 }
				 catch (IOException e) {
					 e.printStackTrace(); 
				 }
			}
			
		}
		return new ResponseEntity<String>("A",HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_CUSTOMER') or hasAnyAuthority('ROLE_BUILDER')")
	@RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ArrayList<PropertyEntity>> findAll(HttpServletRequest request){
		return new ResponseEntity<ArrayList<PropertyEntity>>(this.propertyService.findAll(),HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_CUSTOMER') or hasAnyAuthority('ROLE_BUILDER')")
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PropertyEntity> findById(@PathVariable(required = true) Long id){
		PropertyEntity entity = null;
		try {
			entity = this.propertyService.findById(id);
			System.err.println(entity.getBids().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new ResponseEntity<PropertyEntity>(entity,HttpStatus.OK);
	}
	
	
}
