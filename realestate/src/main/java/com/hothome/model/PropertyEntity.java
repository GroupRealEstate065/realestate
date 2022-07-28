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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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

	@NotBlank(message = "Property Name cannot be Empty")
	@Size(min = 2, max = 35, message = " Property Name must have length of 2-5 characters")
	@Pattern(regexp = "[A-Za-z']*",message = "First Name contains illegal characters")
	private String name;
	
	@NotBlank(message = "Room Details cannot be Empty")
	@Size(min = 1, max = 15, message = " Room Details must have length of 2-15 characters")
	@Pattern(regexp = "[0-9]+",message = "Room Details: Only Numeric value's Allowed")
	private String roomDetails;
	
	@NotBlank(message = "Property Description cannot be Empty")
	@Size(min = 1, max = 35, message = " Room Description must have length of 2-35 characters")
	//@Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Property Description: Only AlphaNumeric Value's Allowed")
	private String description;
	
	private boolean parking;
	
	@NotBlank(message = "Living Area Info cannot be Empty")
	@Size(min = 2, max = 10, message = " Living Area must have length of 2-10 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",message = "Living Area: Only AlphaNumeric Value's Allowed")
	private String livingArea;
	
	@NotBlank(message = "Bathroom Details cannot be Empty")
	@Size(min = 2, max = 10, message = "Bathroom Details must have length of 2-10 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",  message = "Bathroom Details: Only AlphaNumeric Value's Allowed")
	private String bathroomDetails;
	
	@NotBlank(message = "Builder Price cannot be Empty")
	@Size(min = 2, max = 10, message = "Builder Price must have length of 2-10 characters")
	@Pattern(regexp = "[0-9]+", message = "Builder Price: Only Numberic Value's Allowed")
	private String builderPrice;
	
	@NotBlank(message = "Customer Price cannot be Empty")
	@Size(min = 1, max = 10, message = " Living Area must have length of 2-10 characters")
	@Pattern(regexp = "[0-9]+", message = "Customer Price: Only Numberic Value's Allowed")
	private String customerPrice;
	
	@NotBlank(message = "Property Type cannot be Empty")
	@Size(min = 2, max = 10, message = "Property Type must have length of 2-10 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",  message = "Property Type: Only AlphaNumeric Value's Allowed")
	private String propertyType;
	
	@NotBlank(message = "Street cannot be Empty")
	@Size(min = 2, max = 10, message = "Street info must have length of 2-10 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",  message = "Street Info: Only AlphaNumeric Value's Allowed")
	private String street;
	
	@NotBlank(message = "City cannot be Empty")
	@Size(min = 2, max = 10, message = "City Info must have length of 2-10 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",  message = "City Info: Only AlphaNumeric Value's Allowed")
	private String city;
	
	@NotBlank(message = "Postal Code cannot be Empty")
	@Size(min = 2, max = 10, message = "Postal Code must have length of 2-10 characters")
	@Pattern(regexp = "^[a-zA-Z0-9]+$",  message = "Postal Code: Only AlphaNumeric Value's Allowed")
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


	public PropertyEntity(
			@NotBlank(message = "Property Name cannot be Empty") @Size(min = 2, max = 35, message = " Property Name must have length of 2-5 characters") @Pattern(regexp = "[A-Za-z']*", message = "First Name contains illegal characters") String name,
			@NotBlank(message = "Room Details cannot be Empty") @Size(min = 1, max = 15, message = " Room Details must have length of 2-15 characters") @Pattern(regexp = "[0-9]+", message = "Room Details: Only Numeric value's Allowed") String roomDetails,
			@NotBlank(message = "Property Description cannot be Empty") @Size(min = 1, max = 35, message = " Room Description must have length of 2-35 characters") String description,
			boolean parking,
			@NotBlank(message = "Living Area Info cannot be Empty") @Size(min = 2, max = 10, message = " Living Area must have length of 2-10 characters") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Living Area: Only AlphaNumeric Value's Allowed") String livingArea,
			@NotBlank(message = "Bathroom Details cannot be Empty") @Size(min = 2, max = 10, message = "Bathroom Details must have length of 2-10 characters") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Bathroom Details: Only AlphaNumeric Value's Allowed") String bathroomDetails,
			@NotBlank(message = "Builder Price cannot be Empty") @Size(min = 2, max = 10, message = "Builder Price must have length of 2-10 characters") @Pattern(regexp = "[0-9]+", message = "Builder Price: Only Numberic Value's Allowed") String builderPrice,
			@NotBlank(message = "Customer Price cannot be Empty") @Size(min = 1, max = 10, message = " Living Area must have length of 2-10 characters") @Pattern(regexp = "[0-9]+", message = "Customer Price: Only Numberic Value's Allowed") String customerPrice,
			@NotBlank(message = "Property Type cannot be Empty") @Size(min = 2, max = 10, message = "Property Type must have length of 2-10 characters") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Property Type: Only AlphaNumeric Value's Allowed") String propertyType,
			@NotBlank(message = "Street cannot be Empty") @Size(min = 2, max = 10, message = "Street info must have length of 2-10 characters") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Street Info: Only AlphaNumeric Value's Allowed") String street,
			@NotBlank(message = "City cannot be Empty") @Size(min = 2, max = 10, message = "City Info must have length of 2-10 characters") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "City Info: Only AlphaNumeric Value's Allowed") String city,
			@NotBlank(message = "Postal Code cannot be Empty") @Size(min = 2, max = 10, message = "Postal Code must have length of 2-10 characters") @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Postal Code: Only AlphaNumeric Value's Allowed") String postalCode,
			String[] imagesUrl) {
		
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
