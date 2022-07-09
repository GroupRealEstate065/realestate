package com.hothome.model;

import static com.hothome.Utility.AwsS3Constant.*;

import javax.persistence.Entity;
import javax.persistence.Transient;

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
	
	@Transient
	public String[] getImagesUrl() {
		//return S3_BASE_URI + "/user-photos/" + this.id + "/" + this.profileImage;
		for(int i = 0; i < imagesUrl.length; i++) {
			this.imagesUrl[i] =  S3_BASE_URI + "/" + PROPERTY_PHOTOS  + "/" + this.id + "/" + this.imagesUrl[i];
		}
		return this.imagesUrl;
	}
}
