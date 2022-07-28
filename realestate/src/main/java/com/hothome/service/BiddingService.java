package com.hothome.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hothome.dto.LoginDto;
import com.hothome.dto.UserProfileDto;
import com.hothome.model.BiddingEntity;
import com.hothome.model.PropertyEntity;
import com.hothome.model.UserEntity;
import com.hothome.repository.BiddingRepository;

@Service
@Transactional
public class BiddingService {

	@Autowired
	private BiddingRepository biddingRepository;
	
	@Autowired
	private PropertyService propertyService;

	@Autowired
	private UserService userService;
	
	public BiddingEntity save(Double price, Long propId, Long userId) throws Exception {
		
		PropertyEntity propEntity = this.propertyService.findById(propId);
		UserEntity userEntity = this.userService.findById(userId);
		BiddingEntity entity = new BiddingEntity(price, propEntity, userEntity);
		
		propEntity.addBid(entity);
		//this.propertyService.save(propEntity);
		 
		BiddingEntity savedEntity = this.biddingRepository.save(entity);
		
		return savedEntity;
	}
	
	public BiddingEntity findById(Long id) throws Exception {
		Optional<BiddingEntity> bid = this.biddingRepository.findById(id);
		
		if(bid == null) {
			throw new Exception("NO Bid Find");
		}
		else {
			return bid.get();
		}
	}

	public PropertyEntity saveFinalBid(Long bidId, Long propertyId, Long userId, String type) throws Exception {
		BiddingEntity bid = this.findById(bidId);
		PropertyEntity propEntity = this.propertyService.findById(propertyId);
		if(type.equalsIgnoreCase("Customer")) {
			//propEntity.setCustomerFinalBid(bid);
		}
		else {
			//propEntity.setBuilderFinalBid(bid);
		}
		
		return this.propertyService.save(propEntity);
	}
	
	public UserProfileDto findBidsByUser(Long id) throws Exception {
		ArrayList<BiddingEntity> bids = (ArrayList<BiddingEntity>) this.biddingRepository.findBidsByUser(id);
		if(bids.size() > 0) {
			UserProfileDto dto = new UserProfileDto(bids.get(0).getUser(), bids);
			return dto;
		}
		else {
			throw new Exception("Not Found");
		}
	}
}
