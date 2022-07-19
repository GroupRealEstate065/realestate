package com.hothome.model;

import static com.hothome.Utility.AwsS3Constant.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyEntity extends AbstractEntity{

	private String name;
	private String roomDetails;
	private String description;
	private boolean parking;
	private String livingArea;
	private String bathroomDetails;
	private String builderPrice;
	private String customerPrice;
	private String propertyType;
	private String street;
	private String city;
	private String postalCode;
	private String[] imagesUrl;
	
	@JsonManagedReference
	@OneToMany(mappedBy = "property", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<BiddingEntity> bids = new ArrayList<BiddingEntity>();
	
	
	  @OneToOne(fetch = FetchType.EAGER)
	  private BiddingEntity customerFinalBid;
	  
	  @OneToOne(fetch = FetchType.EAGER)
	  private BiddingEntity builderFinalBid;
	  
	 
	@Transient
	public String[] getImagesUrl() { 
		//return S3_BASE_URI + "/user-photos/" + this.id + "/" + this.profileImage;
		for(int i = 0; i < imagesUrl.length; i++) {
			this.imagesUrl[i] =  S3_BASE_URI + "/" + PROPERTY_PHOTOS  + "/" + this.id + "/" + this.imagesUrl[i];
		}
		return this.imagesUrl;
	}


	public void addBid(BiddingEntity entity) {
		this.bids.add(entity);
	}
	
	public PropertyEntity( String name, String roomDetails, String description, boolean parking,
			String livingArea, String bathroomDetails, String builderPrice, String customerPrice, String propertyType,
			String street, String city, String postalCode, String[] imagesUrl) {
		
		this.name = name;
		this.roomDetails = roomDetails;
		this.description = description;
		this.parking = parking;
		this.livingArea = livingArea;
		this.bathroomDetails = bathroomDetails;
		this.builderPrice = builderPrice;
		this.customerPrice = customerPrice;
		this.propertyType = propertyType;
		this.street = street;
		this.city = city;
		this.postalCode = postalCode;
		this.imagesUrl = imagesUrl;
	}
}
