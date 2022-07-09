package com.hothome.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hothome.model.PropertyEntity;

@Repository
public interface PropertyRepository extends PagingAndSortingRepository<PropertyEntity, Long>{
	
}
