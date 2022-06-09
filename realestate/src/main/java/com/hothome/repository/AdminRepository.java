package com.hothome.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.hothome.model.AdminEntity;

public interface AdminRepository extends PagingAndSortingRepository<AdminEntity, Long>{

	@Query("SELECT u FROM AdminEntity u WHERE u.email = :email")
	public AdminEntity getAdminEntityByEmail(@Param("email")String email);
}
