package com.hothome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import static com.hothome.constant.SecurityConstant.*;

import com.hothome.configuration.UserDetailsServiceImpl;
import com.hothome.jwt.JwtTokenProvider;
import com.hothome.model.AdminEntity;
import com.hothome.model.UserLogged;
import com.hothome.model.UserPrincipal;
import com.hothome.repository.AdminRepository;
import com.hothome.service.AdminService;

@RestController
public class HomeController {

	public static final String EMAIL_SENT = "An email with a new password was sent to: ";
    public static final String USER_DELETED_SUCCESSFULLY = "User deleted successfully";
    private AuthenticationManager authenticationManager;
    private UserDetailsServiceImpl userService;
    private JwtTokenProvider jwtTokenProvider;
	
    @Autowired
    private AdminService adminService;
    
    
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
	
	@RequestMapping(value = "/register/admin",method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<AdminEntity> registerAdmin(@RequestBody AdminEntity admin){
		
		String password = admin.getPassword();
		AdminEntity user = adminService.save(admin);
		
		  authenticate(admin.getEmail(),password); UserPrincipal userPrincipal =
		  (UserPrincipal) userService.loadUserByUsername(admin.getEmail()); HttpHeaders
		  jwtHeader = getJwtHeader(userPrincipal);
		 
		
		
		return new ResponseEntity<AdminEntity>(admin,jwtHeader, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/login",method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<UserPrincipal> login(@RequestParam String username, @RequestParam String password){
		
		authenticate(username,password);
		UserPrincipal userPrincipal = (UserPrincipal) userService.loadUserByUsername(username);
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
