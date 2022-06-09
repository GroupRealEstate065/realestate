package com.hothome.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@RequestMapping(value = "/index", method = RequestMethod.GET,produces = "application/json")
	public ResponseEntity<String> index(){
		return new ResponseEntity<String>("Admin",HttpStatus.OK);
	}
}
