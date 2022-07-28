package com.hothome.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.hothome.model.BiddingEntity;
import com.hothome.model.UserEntity;

@Repository
public interface BiddingRepository extends PagingAndSortingRepository<BiddingEntity, Long>{	
	@Query(value = "SELECT * FROM bidding_entity b WHERE b.user_id = ?1", nativeQuery = true)
	public List<BiddingEntity> findBidsByUser(Long id);
}
