package com.hothome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.checkerframework.checker.units.qual.min;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hothome.constant.Roles;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.hothome.Utility.AwsS3Constant.*;

import java.util.Arrays;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity extends AbstractEntity{
	
	@Column(nullable = false)
	@NotBlank(message = "First Name cannot be Empty")
	@Size(min = 2, max = 35, message = "First Name must have length of 2-5 characters ")
	@Pattern(regexp = "[A-Za-z']*",message = "First Name contains illegal characters")
	private String firstName;

	@NotBlank(message = "Last Name cannot be Empty")
	@Size(min = 2, max = 25, message = "Last Name must have length of 2-5 characters ")
	@Pattern(regexp = "[A-Za-z']*",message = "Last Name contains illegal characters")
	@Column(nullable = false)
	private String lastName;
	
	
	@Column(nullable = true, name = "authorities")
	protected String[] authorities;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Roles role;

	@Column(nullable = true)
	protected boolean isActive;
	
	@Column(nullable = true)
	protected boolean isNotLocked;
	
	@Column(unique = true, nullable = false)
	@NotBlank(message = "Email cannot be Empty")
	@Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Invalid Email Type")
	private String email;
	
	@Column(nullable = false)
	@NotBlank(message = "Password Cannot be Empty")
	@Size(min = 2, max = 200, message = "Passowrd must be 2-30 length long")
	//@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Invalid Password")
	@JsonIgnore
	private String password;

	@Column(nullable = true, name = "authenticationType")
	private String authenticationType;
	
	@Column(nullable = true)
	@Size(min = 2, max = 50)
	//@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Please Enter Alphanumeric Characters")
	public String profileImage;
	
	@Size(min = 6, max = 15)
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Please Enter Alphanumeric Characters")
	private String phoneNumber;
	
	@Size(min = 4, max = 25)
	@Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Please Enter Alphanumeric Characters")
	private String street;
	
	@Size(min = 2, max = 25)
	@Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Please Enter Alphanumeric Characters")
	private String city;
	
	@Size(min = 2, max = 25)
	@Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Please Enter Alphanumeric Characters")
	private String postalCode;
	
	@Size(min = 2, max = 25)
	@Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Please Enter Alphanumeric Characters")
	private String licenseNumber;
	
	//@Pattern(regexp = "^[a-zA-Z0-9\\s]+$", message = "Please Enter Alphanumeric Characters")
	private String licenseDoc;
	
	
	
	@Transient
	public String getProfileImageUrl() {
		if(this.id == null || this.profileImage == null) {
			return S3_BASE_URI + "/user-photos/default-user.png";
		}
		else {
			return S3_BASE_URI + "/user-photos/" + this.id + "/" + this.profileImage;
		}
	}



	@Override
	public String toString() {
		return "UserEntity [firstName=" + firstName + ", lastName=" + lastName + ", authorities="
				+ Arrays.toString(authorities) + ", role=" + role + ", isActive=" + isActive + ", isNotLocked="
				+ isNotLocked + ", email=" + email + ", password=" + password + ", authenticationType="
				+ authenticationType + ", profileImage=" + profileImage + ", phoneNumber=" + phoneNumber + ", street="
				+ street + ", city=" + city + ", postalCode=" + postalCode + ", licenseNumber=" + licenseNumber
				+ ", licenseDoc=" + licenseDoc + ", id=" + id + ", createdAt=" + createdAt + ", modifiedAt="
				+ modifiedAt + "]";
	}
	
	
	
}
