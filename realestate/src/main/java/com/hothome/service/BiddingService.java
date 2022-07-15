package com.hothome.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		BiddingEntity savedEntity = this.biddingRepository.save(entity);
		
		return savedEntity;
	}
	
	
}
