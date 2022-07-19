package com.hothome.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hothome.model.UserEntity;
import com.hothome.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<String> index(){
		return new ResponseEntity<String>("Admin",HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<UserEntity> findById(@PathVariable(name = "id", required = true) Long id) throws UsernameNotFoundException{
		UserEntity user = this.userService.findById(id);
		return new ResponseEntity<UserEntity>(user,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/deleteById/{id}", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<Boolean> deleteById(@PathVariable(name = "id", required = true) Long id) throws UsernameNotFoundException{
		boolean status = this.userService.deleteById(id);
		return new ResponseEntity<Boolean>(status,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listAll",method = RequestMethod.GET ,produces = "application/json")
	public ResponseEntity<ArrayList<UserEntity>> listAllUser(){
		ArrayList<UserEntity> list = this.userService.listAll();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@RequestMapping(value = "/checkEmail", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<String> checkEmailUnique(@RequestParam(name = "email", required = true) String email) throws UsernameNotFoundException{
		String response = this.userService.checkEmailUnique(email);
		return new ResponseEntity<String>(response,HttpStatus.OK);
	}
	
}
