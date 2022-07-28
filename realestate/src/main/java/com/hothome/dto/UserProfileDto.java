package com.hothome.dto;

import java.util.ArrayList;
import java.util.Set;

import com.hothome.model.BiddingEntity;
import com.hothome.model.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileDto {
	
	private UserEntity user;
	private ArrayList<BiddingEntity> bids;

}
