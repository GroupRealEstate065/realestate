package com.hothome.model;

import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import static java.util.Arrays.stream;

public class UserPrincipal implements UserDetails{

	private UserLogged user;
	
	public UserPrincipal(UserLogged user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return stream(this.user.getAuthorities()).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		//return true;
		return this.user.isNotLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.user.isActive();
	}
	
	
	
}
