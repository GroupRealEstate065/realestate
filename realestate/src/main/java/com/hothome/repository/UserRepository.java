package com.hothome.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import com.hothome.model.UserEntity;

public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>{

	@Query("SELECT u FROM UserEntity u WHERE u.email = :email")
	public UserEntity getUserEntityByEmail(@Param("email")String email);
	
	@Modifying
	@Query("UPDATE UserEntity u SET u.authenticationType =?2 WHERE u.id = ?1")
	public void updateAuthenticationType(Long userId,String type);
	
}
