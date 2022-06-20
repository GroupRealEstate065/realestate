package com.hothome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.hothome.constant.SecurityConstant.*;

import com.hothome.configuration.UserDetailsServiceImpl;
import com.hothome.constant.AuthenticationType;
import com.hothome.dto.LoginDto;
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
	
	@RequestMapping(value = "/register",method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserEntity> registerAdmin(@RequestBody UserEntity admin){
		
		String password = admin.getPassword();
		admin.setAuthenticationType(AuthenticationType.Database.toString());
		UserEntity user = adminService.save(admin);
		
		  authenticate(admin.getEmail(),password); 
		  UserPrincipal userPrincipal =
		  (UserPrincipal) userService.loadUserByUsername(admin.getEmail()); HttpHeaders
		  jwtHeader = getJwtHeader(userPrincipal);
		
		return new ResponseEntity<UserEntity>(admin,jwtHeader, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/")
	public ResponseEntity<UserEntity> authUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
		UserEntity user = new UserEntity();
		return new ResponseEntity<UserEntity>(user,jwtHeader, HttpStatus.CREATED);
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
    }
	
}
