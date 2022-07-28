package com.hothome.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.hothome.dto.UserProfileDto;
import com.hothome.exception.user.UserNotFoundException;
import com.hothome.model.BiddingEntity;
import com.hothome.model.UserEntity;
import com.hothome.service.BiddingService;
import com.hothome.service.UserService;
import com.supportportal.exception.domain.EmailExistException;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private BiddingService biddingService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<String> index(){
		return new ResponseEntity<String>("Admin",HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<UserEntity> findById(@PathVariable(name = "id", required = true) Long id) throws UsernameNotFoundException, UserNotFoundException{
		UserEntity user = this.userService.findById(id);
		return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
	}
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<Boolean> deleteById(@PathVariable(name = "id", required = true) Long id) throws UsernameNotFoundException, UserNotFoundException{
		boolean status = this.userService.deleteById(id);
		return new ResponseEntity<Boolean>(status,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/listAll",method = RequestMethod.GET ,produces = "application/json")
	public ResponseEntity<ArrayList<UserEntity>> listAllUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		
		System.err.println(authentication);
		ArrayList<UserEntity> list = this.userService.listAll();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/checkEmail", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<String> checkEmailUnique(@RequestParam(name = "email", required = true) String email) throws UsernameNotFoundException, EmailExistException{
		String response = this.userService.checkEmailUnique(email);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST,produces = "application/json")
	public ResponseEntity<ArrayList<UserEntity>> updateActiveStatus(@RequestParam(name = "id", required = true) Long id) throws UsernameNotFoundException, UserNotFoundException{
		ArrayList<UserEntity> list = this.userService.updateActiveStatus(id);
		return new ResponseEntity<ArrayList<UserEntity>>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/profile/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<UserProfileDto> userProfile(@PathVariable() Long id) {
		
		try {
			return new ResponseEntity<UserProfileDto>(this.biddingService.findBidsByUser(id), HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<UserProfileDto>(new UserProfileDto(), HttpStatus.OK);
			
		}
	}
}
