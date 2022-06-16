package com.hothome.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.hothome.constant.Roles;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends AbstractEntity{
	
	@Column(nullable = false)
	private String name;

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
	private String email;
	
	@Column(nullable = false)
	private String password;

	@Column(nullable = true, name = "authenticationType")
	private String authenticationType;

}
