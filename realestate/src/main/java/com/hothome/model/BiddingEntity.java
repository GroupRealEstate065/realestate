package com.hothome.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
	
	@ManyToOne
	@JoinColumn(name = "property_id")
	@JsonBackReference
	private PropertyEntity property;
	 
	@OneToOne
	private UserEntity user;
	
	private boolean aprroved;

	public BiddingEntity(Double price, PropertyEntity property, UserEntity user) {
		this.price = price;
		this.property = property;
		this.user = user;
	}
	
	
}
