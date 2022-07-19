package com.hothome.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping(method = RequestMethod.POST, value = "/save")
	public ResponseEntity<PropertyEntity> saveProperty(@RequestParam String name, @RequestParam String roomDetails,@RequestParam String description,@RequestParam boolean parking,
			@RequestParam String livingArea, @RequestParam String bathroomDetails, @RequestParam String builderPrice, @RequestParam String customerPrice,
			@RequestParam String propertyType, @RequestParam String street, @RequestParam String city, @RequestParam String postalCode,
			@RequestParam(required = false) MultipartFile[] files) 
	{
		String[] filesNameArray;
		PropertyEntity entity;
		if(files.length > 0) {
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
		if(files.length > 0) {
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
		return new ResponseEntity<PropertyEntity>(savedProperty,HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = "application/json")
	public ArrayList<PropertyEntity> findAll(HttpServletRequest request){
		String token= request.getHeader(HttpHeaders.AUTHORIZATION);
		//System.err.println(this.pro.getSubject(token));
		return this.propertyService.findAll();
	}
	
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<PropertyEntity> findById(@PathVariable(required = true) Long id){
		PropertyEntity entity = null;
		try {
			entity = this.propertyService.findById(id);
			//ArrayList<BiddingEntity> bids = (ArrayList<BiddingEntity>) entity.getBids();
			System.err.println(entity.getBids().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new ResponseEntity<PropertyEntity>(entity,HttpStatus.OK);
	}
	
	
}
