package com.hothome.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BiddingEntity extends AbstractEntity{

	private Double price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private PropertyEntity property;
	 
	@OneToOne
	private UserEntity user;
	
	
}
