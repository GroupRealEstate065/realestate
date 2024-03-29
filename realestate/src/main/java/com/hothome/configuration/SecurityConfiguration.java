package com.hothome.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hothome.configuration.customer.DatabaseLoginSuccessHandler;
import com.hothome.configuration.oAuth.CustomerOAuth2UserService;
import com.hothome.configuration.oAuth.OAuth2LoginSuccessHandler;
import com.hothome.filter.JwtAccessDeniedHandler;
import com.hothome.filter.JwtAuthenticationEntryPoint;
import com.hothome.filter.JwtAuthorizationFilter;
import static com.hothome.constant.SecurityConstant.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	private JwtAuthorizationFilter jwtAuthorizationFilter;
	private JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	private UserDetailsService userDetailsService;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired private CustomerOAuth2UserService oAuth2UserService;
	@Autowired private OAuth2LoginSuccessHandler oauth2LoginHandler;
	@Autowired private DatabaseLoginSuccessHandler databaseLoginHandler;
	
	
	@Autowired
	public SecurityConfiguration(JwtAuthorizationFilter jwtAuthorizationFilter,
			JwtAccessDeniedHandler jwtAccessDeniedHandler, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
			UserDetailsService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		
		this.jwtAuthorizationFilter = jwtAuthorizationFilter;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().cors().and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
		.and()
		.authorizeRequests().antMatchers(PUBLIC_URLS).permitAll()
		.anyRequest().authenticated()
				/*
				 * .and() .oauth2Login() .userInfoEndpoint() .userService(oAuth2UserService)
				 * .and() .successHandler(oauth2LoginHandler)
				 */
		.and()
		.exceptionHandling().accessDeniedHandler(jwtAccessDeniedHandler)
		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		.and()
		.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
}
