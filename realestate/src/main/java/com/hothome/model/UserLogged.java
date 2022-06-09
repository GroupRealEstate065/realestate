package com.hothome.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLogged {	
	private Long id;
	private String email;
	private String name;
	private String password;
	private String role;
	private String[] authorities;
	private boolean isActive;
	private boolean isNotLocked;

}
