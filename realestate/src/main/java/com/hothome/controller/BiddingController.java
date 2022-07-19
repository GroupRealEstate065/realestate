package com.hothome.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<HttpResponse> save(@RequestParam String price, @RequestParam Long propertyId, @RequestParam Long userId){
		BiddingEntity entity = null;
		try {
			Double bidPrice = Double.valueOf(price);
			entity = this.biddingService.save(bidPrice, propertyId, userId);
			Object temp = entity;
			HttpResponse response = new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, "Saved ENtity", "Sccessfully");
			response.setEntity(entity);
			return new ResponseEntity<HttpResponse>(response,HttpStatus.OK);
		} catch (Exception e) {
			HttpResponse response = new HttpResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Erorr Occured", "Sccessfully");
			return new ResponseEntity<HttpResponse>(response,HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value = "/finalBid", method = RequestMethod.POST)
	public ResponseEntity<HttpResponse> saveFinalBid(@RequestParam Long bidId, @RequestParam Long propertyId, @RequestParam Long userId, @RequestParam String type){
		PropertyEntity entity = null;
		try {
			entity = this.biddingService.saveFinalBid(bidId, propertyId, userId,type);
			HttpResponse response = new HttpResponse(HttpStatus.OK.value(), HttpStatus.OK, "Saved ENtity", "Sccessfully");
			response.setEntity(entity);
			return new ResponseEntity<HttpResponse>(response,HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			HttpResponse response = new HttpResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST, "Erorr Occured", "Sccessfully");
			return new ResponseEntity<HttpResponse>(response,HttpStatus.BAD_REQUEST);
		}
	}
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	public ResponseEntity<BiddingEntity> findById(@PathVariable Long id){
		BiddingEntity entity = null;
		try {
			 entity = this.biddingService.findById(id);
			return new ResponseEntity<BiddingEntity>(entity,HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<BiddingEntity>(entity,HttpStatus.BAD_REQUEST);
		}
	}
	
}
