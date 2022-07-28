package com.hothome.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hothome.model.BiddingEntity;
import com.hothome.model.HttpResponse;
import com.hothome.model.PropertyEntity;
import com.hothome.service.BiddingService;

@RestController
@RequestMapping(value = "/bid")
public class BiddingController {

	@Autowired
	private BiddingService biddingService;
	
	@PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER') or hasAnyAuthority('ROLE_BUILDER')")
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<BiddingEntity> save(@RequestParam Double price, @RequestParam Long propertyId, @RequestParam Long userId) throws Exception{
		try {
			BiddingEntity entity = this.biddingService.save(price, propertyId, userId);
			return new ResponseEntity<BiddingEntity>(entity,HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error in save bid");
		}
	}
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@RequestMapping(value = "/finalBid", method = RequestMethod.POST)
	public ResponseEntity<PropertyEntity> saveFinalBid(@RequestParam Long bidId, @RequestParam Long propertyId, @RequestParam Long userId, @RequestParam String type) throws Exception{
		PropertyEntity entity = this.biddingService.saveFinalBid(bidId, propertyId, userId,type);
		return new ResponseEntity<PropertyEntity>(entity,HttpStatus.CREATED);
		
	}
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_CUSTOMER') or hasAnyAuthority('ROLE_BUILDER')")
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	public ResponseEntity<BiddingEntity> findById(@PathVariable Long id){
		BiddingEntity entity = null;
		try {
			 entity = this.biddingService.findById(id);
			return new ResponseEntity<BiddingEntity>(entity,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<BiddingEntity>(entity,HttpStatus.BAD_REQUEST);
		}
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(
	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
}
