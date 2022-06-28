package com.hothome.dto;

import org.springframework.web.multipart.MultipartFile;

import com.hothome.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RegisterDto {
	private UserEntity user;
	private MultipartFile userImage;
	private MultipartFile licenseDoc;
	
}
