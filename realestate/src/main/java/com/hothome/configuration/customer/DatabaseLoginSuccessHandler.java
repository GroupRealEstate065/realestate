package com.hothome.configuration.customer;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.hothome.constant.AuthenticationType;
import com.hothome.model.UserEntity;
import com.hothome.repository.UserRepository;
import com.hothome.service.UserService;

@Component
public class DatabaseLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Autowired
	private UserService userService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();
		UserEntity customer = userDetails.getCustomer();
		
		userService.updateAuthenticationType(customer.getId(), AuthenticationType.Google.toString());
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	
}
