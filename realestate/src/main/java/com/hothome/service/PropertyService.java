package com.hothome.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hothome.model.PropertyEntity;
import com.hothome.repository.PropertyRepository;

@Service
public class PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;
	
	public PropertyEntity save(PropertyEntity entity) {
		return this.propertyRepository.save(entity);
	}
	
	public ArrayList<PropertyEntity> findAll(){
		//ArrayList<E>
		return (ArrayList<PropertyEntity>) this.propertyRepository.findAll();
	}
	
	public PropertyEntity findById(Long id) throws Exception{
		//ArrayList<E>
		Optional<PropertyEntity> entity = this.propertyRepository.findById(id);
		if(entity == null) {
			throw new Exception("NOt Found");
		}
		else {
			
			return entity.get();
		}
		
	}
	
	
	
	
	
	
	
}
