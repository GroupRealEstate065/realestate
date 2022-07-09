package com.hothome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static com.hothome.constant.AuthenticationType.Database;
import static com.hothome.constant.Authority.ADMIN_AUTHORITIES;
import static com.hothome.constant.Roles.ROLE_ADMIN;
import static com.hothome.constant.SecurityConstant.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.MultipartConfig;
import javax.validation.Valid;

import com.hothome.Utility.AmazonS3Util;
import com.hothome.Utility.AwsS3Constant;
import com.hothome.configuration.UserDetailsServiceImpl;
import com.hothome.constant.AuthenticationType;
import com.hothome.constant.Authority;
import com.hothome.constant.Roles;
import com.hothome.dto.LoginDto;
import com.hothome.dto.RegisterDto;
import com.hothome.jwt.JwtTokenProvider;
import com.hothome.model.UserEntity;
import com.hothome.model.UserLogged;
import com.hothome.model.UserPrincipal;

import com.hothome.service.UserService;

@RestController
public class HomeController {

	public static final String EMAIL_SENT = "An email with a new password was sent to: ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userService;
    
    private JwtTokenProvider jwtTokenProvider;
	
    @Autowired
    private UserService adminService;
    
    
    
    
    @Autowired
	public HomeController(AuthenticationManager authenticationManager, UserDetailsServiceImpl userService,
			JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.jwtTokenProvider = jwtTokenProvider;
	}


	@RequestMapping(value = "/status", method = RequestMethod.GET,produces = "application/json")
	public String home() {
		return "UP";
	}
	
	
	@RequestMapping(value = "/registerr",method = RequestMethod.GET,  produces = "application/json")
	public ResponseEntity<UserEntity> registerAdminNew(){
		
	UserEntity entity = new UserEntity();
		
	/*
	 * entity.setFirstName("Karanpartap"); entity.setLastName("Singh");
	 * entity.setAuthorities(ADMIN_AUTHORITIES); entity.setRole(ROLE_ADMIN);
	 * //String pssword = encoder.encode("Aq@123456789");
	 * entity.setPassword("Aq@123456789"); entity.setActive(true);
	 * entity.setNotLocked(true); entity.setEmail("karanpartapsingh20@gmail.com");
	 * entity.setPhoneNumber("14168457419");
	 * entity.setAuthenticationType(Database.toString());
	 * entity.setRole(ROLE_ADMIN); entity.setStreet("Oaklea Blvd");
	 * entity.setPostalCode("L6Y5A2"); entity.setCity("Brampton");
	 * entity.setLicenseDoc("1233APO1"); entity.setLicenseNumber("1233APO1");
	 */
		
	entity.setFirstName("John");
	entity.setLastName("Smith");
	entity.setAuthorities(Authority.USER_AUTHORITIES);
	entity.setRole(Roles.ROLE_CUSTOMER);
	entity.setPassword("Aq@123456789");
	entity.setActive(true);
	entity.setNotLocked(true);
	entity.setEmail("johnsmith@gmail.com");
	entity.setPhoneNumber("14168457419");
	entity.setAuthenticationType(Database.toString());
	entity.setRole(ROLE_ADMIN);
	entity.setStreet("Lennon Drive");
	entity.setPostalCode("LUIOO2");
	entity.setCity("MIssissauga");
	entity.setLicenseDoc("12336APII");
	entity.setLicenseNumber("12336APII");
	
		String password = entity.getPassword();
		entity.setAuthenticationType(AuthenticationType.Database.toString());
		UserEntity user = adminService.save(entity);
		
		  authenticate(entity.getEmail(),password); 
		  UserPrincipal userPrincipal =
		  (UserPrincipal) userService.loadUserByUsername(entity.getEmail()); HttpHeaders
		  jwtHeader = getJwtHeader(userPrincipal);
		
		return new ResponseEntity<UserEntity>(entity,jwtHeader, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/register",method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<UserEntity> registerAdmin(@RequestParam(required = false) Long id,@RequestParam() String firstName,@RequestParam() String lastName,@RequestParam() String email,
			@RequestParam() String phoneNumber,@RequestParam() String password,@RequestParam() String street,
			@RequestParam() String city,@RequestParam() String postalCode,@RequestParam() String licenseNumber,
			@RequestParam() String role, @RequestParam(required = false) MultipartFile userImage, 
			@RequestParam(required = false) MultipartFile builderDoc){
		
		String[] authorities = null;
		Roles roleTemp = null;
		if(role == "ROLE_ADMIN") {
			roleTemp = ROLE_ADMIN;
		}
		else if(role == "ROLE_CUSTOMER") {	
			roleTemp = Roles.ROLE_CUSTOMER;
		}
		else {
			roleTemp = Roles.ROLE_BUILDER;	
		}
		
		if(role == Roles.ROLE_ADMIN.toString()) {
			authorities = ADMIN_AUTHORITIES;
		}
		else if(role == Roles.ROLE_BUILDER.toString()) {
			authorities = ADMIN_AUTHORITIES;	
		}
		else {
			authorities = ADMIN_AUTHORITIES;
		}
		UserEntity entity;
		if(id == null) {
			entity = new UserEntity(firstName, lastName, authorities, roleTemp, true, true, email, password, AuthenticationType.Database.toString(), "", phoneNumber, street, city, postalCode, licenseNumber, "");
		}
		else {
			entity =  new UserEntity(id,firstName, lastName, authorities, roleTemp, true, true, email, password, AuthenticationType.Database.toString(), "", phoneNumber, street, city, postalCode, licenseNumber, "");
		}
		 
		entity.setAuthenticationType(AuthenticationType.Database.toString());
		if(userImage != null && !userImage.isEmpty()) 
		{ 
			 String imageName = userImage.getOriginalFilename(); 
			 entity.setProfileImage(imageName);
		}
		 if(builderDoc != null && !builderDoc.isEmpty()) 
		{ 
			 String imageName = builderDoc.getOriginalFilename(); 
			 entity.setLicenseDoc(imageName); 
		}
		UserEntity savedUser = adminService.save(entity);
		//upload user image
		 if(userImage != null && !userImage.isEmpty()) 
		{ 
			 String imageName = userImage.getOriginalFilename(); 
			 //entity.setProfileImage(imageName); 
			 String uploadDir = AwsS3Constant.USER_PHOTOS +"/" + entity.getId(); 
			 try {
				  AmazonS3Util.deleteFile(uploadDir); 
				  AmazonS3Util.uploadFile(uploadDir,imageName, userImage.getInputStream());
			 }
			 catch (IOException e) {
				 e.printStackTrace(); 
			 }	 
		 }	
		 //upload builder docs
		 if(builderDoc != null && !builderDoc.isEmpty()) 
		{ 
			 String imageName = builderDoc.getOriginalFilename(); 
			 //entity.setLicenseDoc(imageName); 
			 String uploadDir = AwsS3Constant.BUILDER_DOC + "/" + entity.getId(); 
			 try {
				  AmazonS3Util.deleteFile(uploadDir); 
				  AmazonS3Util.uploadFile(uploadDir,imageName, userImage.getInputStream());
			 }
			 catch (IOException e) {
				 e.printStackTrace(); 
			 }	 
		 }	
			
		
		authenticate(entity.getEmail(),password); 
		UserPrincipal userPrincipal = (UserPrincipal) userService.loadUserByUsername(entity.getEmail()); 
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
	
		return new ResponseEntity<UserEntity>(savedUser,jwtHeader, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/user-image/{id}",method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> saveUserImage(@PathVariable(required = true) Long id ,@RequestParam(required = true) MultipartFile userImage){
		UserEntity entity = this.adminService.findById(id);
		 if(!userImage.isEmpty()) 
		{ 
			 String imageName = userImage.getOriginalFilename(); 
			 entity.setProfileImage(imageName); 
			 String uploadDir = "user-photos/" + entity.getId(); 
			 try {
				  AmazonS3Util.deleteFile(uploadDir); 
				  AmazonS3Util.uploadFile(uploadDir,imageName, userImage.getInputStream());
			 }
			 catch (IOException e) {
				 e.printStackTrace(); 
			 } 
		 }
		return new ResponseEntity<String>("Uploaded",HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value = "/login",method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserPrincipal> login(@RequestBody LoginDto loginDto){
		
		authenticate(loginDto.getEmail(),loginDto.getPassword());
		UserPrincipal userPrincipal = (UserPrincipal) userService.loadUserByUsername(loginDto.getEmail());
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
		return new ResponseEntity<UserPrincipal>(userPrincipal,jwtHeader, HttpStatus.CREATED);
	}
	
	
	
	
	private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(user));
        return headers;
    }
	 private void authenticate(String username, String password) {
		 authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));	
		 /*
			 * try { authenticationManager.authenticate(new
			 * UsernamePasswordAuthenticationToken(username, password)); } catch (Exception
			 * e) { // TODO: handle exception throw new
			 * BadCredentialsException("Invalid Username Password"); }
			 */
    }
	
}
