package com.hothome.configuration.oAuth;

import static com.hothome.constant.SecurityConstant.JWT_TOKEN_HEADER;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.hothome.constant.AuthenticationType;
import com.hothome.jwt.JwtTokenProvider;
import com.hothome.model.UserEntity;
import com.hothome.model.UserLogged;
import com.hothome.model.UserPrincipal;
import com.hothome.service.UserService;

@Component
public class OAuth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider; 
	
	
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy(); 
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		CustomerOAuth2User auth2User = (CustomerOAuth2User) authentication.getPrincipal();
		
		String name = auth2User.getName();
		String email = auth2User.getEmail();
		String countryCode = request.getLocale().getCountry();
		String cleintName = auth2User.getClientName();
		
		AuthenticationType authenticationType = getAuthenticationType(cleintName);
		
		UserEntity customer = userService.getUserByEmail(email);
		
		if(customer == null) 
		{
			customer = userService.addNewCustomerUponOAuthLogin(name, email, countryCode, authenticationType);
		}
		else {
			auth2User.setFullName(customer.getFirstName() + " " +customer.getLastName());
			userService.updateAuthenticationType(customer.getId(), cleintName);
		}
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		super.onAuthenticationSuccess(request, response, authentication);
		//redirectStrategy.sendRedirect(request, response, "/auth");
		
	}
	
	private AuthenticationType getAuthenticationType(String cleintName) {
		if(cleintName.equals(AuthenticationType.Google.toString())) {
			return AuthenticationType.Google;
		}
		else {
			return AuthenticationType.Database;
		}
	}
	private HttpHeaders getJwtHeader(UserPrincipal user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(user));
        return headers;
    }
	
}
