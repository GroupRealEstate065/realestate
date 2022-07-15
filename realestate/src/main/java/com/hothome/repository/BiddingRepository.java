package com.hothome.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hothome.model.BiddingEntity;

@Repository
public interface BiddingRepository extends PagingAndSortingRepository<BiddingEntity, Long>{

}
